package com.example.spwork.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudentUpdataDto {

    @NotBlank(message = "姓名不能为空")
    private String stuName;

    @NotBlank(message = "学号不能为空")
    private String stuNo;

    // 密码可选：传了新密码就加密更新，不传则保留原密码
    // （不加 @NotBlank，允许用户只改姓名/学号而不改密码）
    private String stuPsd;

    // stuId 不再由前端传入，改由后端从 JWT Token 中获取
}
