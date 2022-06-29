package com.youfuzhang.filter;
import com.youfuzhang.beans.User;
import com.youfuzhang.exception.LoginException;
import com.youfuzhang.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Component
public class UserFilter implements Filter {
    @Autowired
    UserServices userServices;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String url = req.getRequestURI();
        System.out.println("访问网址是："+url);
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");

        //放行login.html 和 UserController/get.action
        if (url.indexOf("login.html") != -1 || url.indexOf("UserController/get.action") != -1) {
            System.out.println("login.html 和 UserController/get.action放行了");
            chain.doFilter(req,res);
        }
        else {
            if (user != null) {
                System.out.println("doFilter(有session)放行.......");
                chain.doFilter(req,res);
            }else{
                //走个cookies
                String name="";
                String pwd="";
                Cookie[] cookies = req.getCookies();
                for (Cookie cookie : cookies) {
                    //获取name
                    if (cookie.getName().equals("name")) {
                        name= cookie.getValue();
                    }
                    //获取pwd
                    if (cookie.getName().equals("pwd")) {
                        pwd= cookie.getValue();
                    }
                }
                System.out.println("name--" + name);
                System.out.println("pwd--" + pwd);

                try {
                    //业务已经判定了账户过期，账户失效
                    User user1 = userServices.get(name, pwd);
                    if (user1 != null) {
                        //判断用户IP地址是否是允许的数据
                        String allowIps = user1.getAllowIps();
                        if (allowIps != null) {
                            //判定IP地址是否在有效范围内
                            //关于允许访问的IP：为空表示不限制，多个IP使用逗号分隔
                            String localAddr = req.getLocalAddr();
                            //System.out.println("本地IP是：" + localAddr);
                            int i = allowIps.indexOf(localAddr);
                            if (i == -1) {
                                throw new LoginException("doFilter----你的IP地址不在有效范围内");
                            }
                        }
                        HttpSession session1 = req.getSession();
                        session1.setAttribute("user", user1);
                        System.out.println("doFilter--放行了【因为有cookies】----------" + url);
                        chain.doFilter(req, res);
                    }else {
                        System.out.println("doFilter--没有放行----------" + url);
                    }

                } catch (LoginException e) {
                    //throw new RuntimeException(e);
                    res.sendRedirect("/login.html");
                }
            }
        }

    }
}
