package com.youfuzhang.services;
import com.youfuzhang.beans.User;
import com.youfuzhang.exception.LoginException;
import com.youfuzhang.mapper.UserMapper;
import com.youfuzhang.tools.MyMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    UserMapper userMapper;
    @Override
    public User get(String name, String pwd) throws LoginException {
        pwd = MyMD5.MD5("zyf", pwd);
        User user = userMapper.get(name, pwd);
        if (user == null) {
            //用户名或密码错误。
            throw new LoginException("业务发出-----用户名或密码错误");
        }
        if(user.getLockStatus().equals("0")){
            //关于锁定状态：0锁定  1启用
            //用户锁定
            throw new LoginException("业务发出-----用户锁定");

        }

        if(user.getEditTime()!=null){
            String expireTime = user.getExpireTime();
            //用户的时间
            LocalDate localDate=LocalDate.parse(expireTime);
            //当前时间
            LocalDate localDateNow=LocalDate.now();
            //判断当前时间是否在用户时间之后
            boolean after = localDateNow.isAfter(localDate);
            System.out.println("after-----------------------"+after);
            if(after){
                throw new LoginException("业务发出-----用户时间过期");
            }
        }
            //关于允许访问的IP：为空表示不限制，多个IP使用逗号分隔
        return user;
    }
}
