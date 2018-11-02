package sso.service;

import core.vo.R;
import sso.domain.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    //查询全部
    List<User> query();

    //登录和检查
//    R ssoLogin(String token, String name, String password, HttpServletResponse response, HttpSession session);

    //单点登录之登录
    R ssoLogin(String username, String password, HttpServletResponse response);

    //单点登录之检查是否登录
    R ssoCheck(String token, HttpServletResponse response);

    R loginOut(String token, HttpServletResponse response);
}
