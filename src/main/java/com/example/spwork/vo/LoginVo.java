package com.example.spwork.vo;

import lombok.Data;

/**
 * 登录成功返回对象：令牌 + 用户基本信息
 */
@Data
public class LoginVo {
    private String token;
    private Integer stuId;
    private String stuNo;
    private String stuName;
}
