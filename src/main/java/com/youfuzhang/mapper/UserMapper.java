package com.youfuzhang.mapper;
import com.youfuzhang.beans.User;
import org.apache.ibatis.annotations.Param;
public interface UserMapper {
    User get(@Param("name") String name, @Param("pwd") String pwd);


}
