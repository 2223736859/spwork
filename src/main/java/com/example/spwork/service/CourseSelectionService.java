package com.example.spwork.service;

import com.example.spwork.entity.CourseSelection;
import com.example.spwork.mapper.CourseSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface CourseSelectionService {
    /**
     * 选课
     * @param courseId 课程 ID（前端传入）
     * @param stuId    学生 ID（从 JWT Token 中获取，不信任前端）
     */
    void insertCS(Integer courseId, Integer stuId);
}
