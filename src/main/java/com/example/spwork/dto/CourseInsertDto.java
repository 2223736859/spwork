package com.example.spwork.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CourseInsertDto {

    @NotNull(message = "课程号不能为空")
    private Integer courseId;

    @NotNull(message = "学生编号不能为空")
    private Integer stuId;
}
