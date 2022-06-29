package com.youfuzhang.tools;
import org.springframework.util.DigestUtils;
public class MyMD5 {
    public static String MD5(String name ,String pwd) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(pwd);
        String s = DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes());
        return s;
    }
}
