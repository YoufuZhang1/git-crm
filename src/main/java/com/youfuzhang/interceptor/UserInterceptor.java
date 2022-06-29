package com.youfuzhang.interceptor;
import com.youfuzhang.beans.User;
import com.youfuzhang.exception.LoginException;
import com.youfuzhang.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    UserServices userServices;
    /**
     *Controller方法执行之前会被调用
     * @param request  request.getRequestURI() 控制器响应的地址，如果不请求到控制器的话就无法响应了
     * @param response
     * @param handler  是控制器执行的方法的对象HandlerMethod 方法是get,控制器中的方法
     * @return     true是Controller方法可以执行，  false是Controller方法不常用
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle.........." + request.getRequestURI());
        //如果返回为null代表。用户没有输入密码，用户无法 进行后端数据修改
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");
        if (user == null) {
            //判断cookie
            String name = "";
            String pwd = "";
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                //获取name
                if (cookie.getName().equals("name")) {
                    name = cookie.getValue();
                }
                //获取pwd
                if (cookie.getName().equals("pwd")) {
                    pwd = cookie.getValue();
                }
            }
            System.out.println("name--" + name);
            System.out.println("pwd--" + pwd);


            //业务已经判定了账户过期，账户失效
            User user1 = userServices.get(name, pwd);
            if (user1 != null) {
                //判断用户IP地址是否是允许的数据
                String allowIps = user1.getAllowIps();
                if (allowIps != null) {
                    //判定IP地址是否在有效范围内
                    //关于允许访问的IP：为空表示不限制，多个IP使用逗号分隔
                    String localAddr = request.getLocalAddr();
                    //System.out.println("本地IP是：" + localAddr);
                    int i = allowIps.indexOf(localAddr);
                    if (i == -1) {
                        throw new LoginException("preHandle----不放行，你的IP地址不在有效范围内");
                    }
                }
                //用户合格，创造session
                HttpSession session1 = request.getSession();
                session1.setAttribute("user", user1);
                System.out.println("preHandle--放行了【因为有cookies】----------");
                return true;
            } else {
                System.out.println("preHandle--没有放行----------");
            }
        }
        return true;
    }

    /**
     * 请求之后，在Controller的方法执完毕，但是仅仅只有renturn没有执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView 这个是请求对象作用域，一般用于跳转界面的，JSON不用
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle.........."+request.getRequestURI());
        /*System.out.println(handler.getClass().getCanonicalName());
        //org.springframework.web.method.HandlerMethod

        HandlerMethod handlerMethod= (HandlerMethod)handler;
         System.out.println(handlerMethod.getMethod().getName());//get 方法*/
    }

    /**
     *请求之后，在Controller的方法执完毕，且renturn执行之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion.........."+request.getRequestURI());

    }
}
