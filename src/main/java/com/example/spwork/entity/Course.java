package com.example.spwork.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String name;
    private String teacher;
    // 其他课程相关属性

    // 构造方法、Getter和Setter方法

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getteacher() {
        return teacher;
    }

    public void setteacher(String teacher) {
        this.teacher = teacher;
    }

    // 其他课程相关属性的Getter和Setter方法
}
