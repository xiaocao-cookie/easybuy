package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;         //主键
    private String loginName;   //登录名
    private String userName;    //用户名
    private String password;    //密码
    private Integer sex;        //性别
    private String identityCode;//身份证号
    private String email;       //邮箱
    private String mobile;      //手机
    private Integer type;       //类型（1：后台 0:前台）

    public User(String loginName, String userName, String password, Integer sex, String identityCode, String email, String mobile, Integer type) {
        this.loginName = loginName;
        this.userName = userName;
        this.password = password;
        this.sex = sex;
        this.identityCode = identityCode;
        this.email = email;
        this.mobile = mobile;
        this.type = type;
    }
}
