package core.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    //获取Cookie
    public static String getCk(String name, HttpServletRequest request){
        System.out.println("getCK-cookie值："+name);
        Cookie[] arrc=request.getCookies();
        if (arrc != null) {
            for(Cookie c:arrc){
                if(c.getName().equals(name)){
                    System.out.println("getCK-值"+c.getValue());
                    return c.getValue();
                }
            }
        }
        return null;
    }
    //设置Cookie
    public static void setCK(String name,String value,int age, HttpServletResponse response){
        Cookie cookie=new Cookie(name,value);
        cookie.setMaxAge(age);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);
    }
    //删除Cookie
    public static void delCK(String name, HttpServletResponse response){
        Cookie cookie=new Cookie(name,"");
        cookie.setMaxAge(0);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        System.out.println(cookie);
        response.addCookie(cookie);
    }

}
