package com.example.spwork.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class StudentLoginDto {
    @NotBlank(message = "账号不能为空")
    private String stuNo;

    @NotBlank(message = "密码不能为空")
    private String stuPsd;
}
