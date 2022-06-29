package com.youfuzhang.beans;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String id;
    private String deptId;
    private String loginAct;
    private String createBy;
    private String lockStatus;
    private String editTime;
    private String name;
    private String allowIps;
    private String editBy;
    private String expireTime;
    private String loginPwd;
    private String createTime;
    private String email;
}
