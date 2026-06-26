package com.example.spwork.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class StudentUpdataDto {
    @NotNull(message = "编号不能为空")
    private int stuId;
    
    @NotBlank(message = "姓名不能为空")
    private String stuName;

    @NotBlank(message = "学号不能为空")
    private String stuNo;

    @NotBlank(message = "密码不能为空")
    private String stuPsd;
}
