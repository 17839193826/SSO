package sso.service.Impl;

import com.alibaba.fastjson.JSON;
import core.redis.JedisUtil;
import core.util.CookieUtil;
import core.util.IdGenerator;
import core.vo.R;
import core.vo.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sso.domain.User;
import sso.mapper.UserMapper;
import sso.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public List<User> query() {
        return mapper.queryPage(0, 20);
    }

    @Override
    public R ssoLogin(String username, String password, HttpServletResponse response) {
        User user = mapper.queryBy(username);
        if (user != null) {
            if (Objects.equals(password, user.getPassword())) {
                //登录成功
                UserStatus status = mapper.queryStatusByUid(user.getId());
                if (null != status) {
                    System.out.println("有登陆状态");
                    //有登陆状态
                    if (1 == status.getIslogin()) {
                        //已登陆过，踢出前登陆账号 删除redis中token值
                        jedisUtil.delKey(status.getToken());
                        CookieUtil.setCK("userauth", "", 0, response);
                    }
                    //登陆成功设置登陆状态为1
                    mapper.updateStatusById(1, user.getId());
                    long tk = idGenerator.nextId();
                    jedisUtil.addStr(""+tk, JSON.toJSONString(user.getUsername()));
                    jedisUtil.expire(""+tk, TimeUnit.MINUTES, 30);
                    CookieUtil.setCK("userauth", ""+tk, -1, response);
                    UserStatus userStatus = new UserStatus(1, "" + tk, user.getId());
                    mapper.updateAllStatus(userStatus);
                    return new R(0, "登录成功", user.getUsername());
                } else {
                    System.out.println("没有登陆状态");
                    //没有登陆状态
                    long tk = idGenerator.nextId();
                    System.out.println("--===================tk:"+tk+" ============="+JSON.toJSONString(user.getUsername()));
                    jedisUtil.addStr("" + tk, JSON.toJSONString(user.getUsername()));
                    jedisUtil.expire("" + tk, TimeUnit.MINUTES, 30);
                    CookieUtil.setCK("userauth", "" + tk, -1, response);
                    UserStatus status1 = new UserStatus(1, "" + tk, user.getId());
                    //添加登陆状态
                    System.out.println("添加登陆状态");
                    int i = mapper.addStatus(status1);
                    if (i > 0) {
                        System.out.println("状态添加成功");
                    }
                    return new R(0, "登录成功", user.getUsername());
                }
            } else {
                //密码不正确
                return R.setError("密码不正确");
            }
        }
        //用户名不存在
        return R.setError("用户名不存在");
    }

    @Override
    public R ssoCheck(String token, HttpServletResponse response) {
        System.out.println("ssocheck-token:" + token);
        if (token != null) {
            if (jedisUtil.isKey(token)) {
                //存在就刷新时间
                jedisUtil.expire(token, TimeUnit.MINUTES, 30);
                return R.setOK("存在");
            } else {
                //令牌失效  删除Cookie
                CookieUtil.delCK("userauth", response);
                return R.setError("不存在");
            }
        } else {
            return R.setError("不存在");
        }

    }

    @Override
    public R loginOut(String token, HttpServletResponse response) {
        System.out.println("退出登陆："+token);
        if (token == null) {
            System.out.println("loginout------:token为空");
            CookieUtil.setCK("userauth", "", 0, response);
        } else {
            //设置登陆状态为未登录
            mapper.updateStatus(0, token);
            //移除Redis
            jedisUtil.delKey(token);
            //移除Cookie
            CookieUtil.setCK("userauth", "", 0, response);
            mapper.updateStatus(0, token);
        }
        return R.setOK("注销成功");
    }
}
