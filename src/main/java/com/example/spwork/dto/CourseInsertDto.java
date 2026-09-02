package com.example.spwork.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CourseInsertDto {

    @NotNull(message = "课程号不能为空")
    private Integer courseId;

    // stuId 不再由前端传入，改由后端从 JWT Token 中获取（UserContext.getStuId()）
}
