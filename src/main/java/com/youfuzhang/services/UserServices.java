package com.youfuzhang.services;

import com.youfuzhang.beans.User;
import com.youfuzhang.exception.LoginException;
import org.apache.ibatis.annotations.Param;

public interface UserServices {
    User get( String name,  String pwd) throws LoginException;
}
