package com.example.spwork.service;

import com.example.spwork.dto.CourseSearchDto;
import com.example.spwork.vo.CourseVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {
    List<CourseVo> getAllCourses();

    List<CourseVo> search(CourseSearchDto dto);

    List<CourseVo> getOneCourse(Integer selectId);

    void delcourse(int courseId,int stuId);
}
