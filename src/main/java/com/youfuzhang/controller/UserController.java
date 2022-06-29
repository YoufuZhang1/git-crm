package com.youfuzhang.controller;
import com.youfuzhang.beans.User;
import com.youfuzhang.exception.LoginException;
import com.youfuzhang.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/UserController")
public class UserController {

    @Autowired
    UserServices userServices;

    @RequestMapping("get.action")
    public Map get(String name, String pwd, boolean isAutoLogin, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws LoginException {
        User user = userServices.get(name, pwd);

        //判断用户IP地址是否是允许的数据
        String allowIps = user.getAllowIps();
        if (allowIps != null) {
            //判定IP地址是否在有效范围内
            //关于允许访问的IP：为空表示不限制，多个IP使用逗号分隔
            String localAddr = httpServletRequest.getLocalAddr();
            System.out.println("本地IP是：" + localAddr);
            int i = allowIps.indexOf(localAddr);
            if (i == -1) {
               throw new LoginException("控制器发出----你的IP地址不在有效范围内");
            }
        }
        HashMap hashMap = new HashMap();
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("user",user);
        System.out.println(isAutoLogin);
        //自动登录、
        if (isAutoLogin) {
            //存储到请求的本地机的cookies里面（cookies必须开）
            Cookie cookieName = new Cookie("name", name);
            //作用域
            cookieName.setPath("/");
            //有效时间(10天)
            cookieName.setMaxAge(10*24*60*60);

            Cookie cookiePwd = new Cookie("pwd", pwd);
            //作用域
            cookiePwd.setPath("/");
            //有效时间(10天)
            cookiePwd.setMaxAge(10*24*60*60);
            //告诉浏览器添加两个cookies
            httpServletResponse.addCookie(cookieName);
            httpServletResponse.addCookie(cookiePwd);
        }
        
        hashMap.put("error",0);
        return hashMap;
    }

    @RequestMapping("getUser.action")
    public Map getUser(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        Object user = session.getAttribute("user");
        HashMap hashMap = new HashMap();
        if (user != null) {
            hashMap.put("success",1);
            hashMap.put("data",user);
        }else {
            hashMap.put("success",0);
            hashMap.put("mess","信息错误");
        }
        return hashMap;
    }

    @RequestMapping("close.action")
    public Map close(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
        Cookie cookieName = new Cookie("name", "xx");
        //作用域
        cookieName.setPath("/");
        //有效时间(10天)
        cookieName.setMaxAge(0);
        Cookie cookiePwd = new Cookie("pwd", "xx");
        //作用域
        cookiePwd.setPath("/");
        //有效时间(10天)
        cookiePwd.setMaxAge(0);
        //告诉浏览器添加两个cookies
        httpServletResponse.addCookie(cookieName);
        httpServletResponse.addCookie(cookiePwd);
        HashMap hashMap = new HashMap();
        hashMap.put("success",1);
        return hashMap;
    }

}
