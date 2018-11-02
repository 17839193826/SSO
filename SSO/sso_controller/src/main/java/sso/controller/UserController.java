package sso.controller;

import core.util.CookieUtil;
import core.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sso.domain.User;
import sso.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    //查询全部
    @ResponseBody
    @GetMapping("/queryUsers")
    public List<User> queryAll() {
        return service.query();
    }

    //登录
    @PostMapping("/userlogin")
    @ResponseBody
    public R login(String username, String password, HttpServletResponse response) {
        return service.ssoLogin(username, password, response);
    }

    //检查登录
    @GetMapping("/usercheck")
    @ResponseBody
    public R checkLogin(HttpServletRequest request, HttpServletResponse response) {
        String tk = CookieUtil.getCk("userauth", request);
        System.out.println("usercheck-controller-令牌：" + tk);
        if (tk == null) {
            return new R(10,"值为空",null);
        }
        return service.ssoCheck(tk, response);
    }

    //注销
    @GetMapping("/userout")
    @ResponseBody
    public R loginout(HttpServletRequest request, HttpServletResponse response) {
        String tk = CookieUtil.getCk("userauth", request);
            return service.loginOut(tk, response);
    }

}
