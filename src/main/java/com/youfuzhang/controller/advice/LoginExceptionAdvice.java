package com.youfuzhang.controller.advice;
import com.youfuzhang.exception.LoginException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
//增强控制器 抓全局异常
@RestControllerAdvice
public class LoginExceptionAdvice {
    //抓哪种类型的异常
    @ExceptionHandler({LoginException.class})
    public Map login(LoginException e){
        HashMap hashMap = new HashMap();
        hashMap.put("error",1);
        hashMap.put("mess",e.getMessage());
        return hashMap;
    }
}
