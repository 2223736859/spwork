package com.example.spwork.service.impl;

import com.example.spwork.dto.CourseSearchDto;
import com.example.spwork.entity.Course;
import com.example.spwork.exception.BusinessException;
import com.example.spwork.mapper.CourseMapper;
import com.example.spwork.mapper.CourseSelectionMapper;
import com.example.spwork.service.CourseService;
import com.example.spwork.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;

    @Autowired
    CourseSelectionMapper courseSelectionMapper;

    @Override
    public List<CourseVo> getAllCourses() {
        List<Course> courses = courseMapper.getAllCourses();
        return courses.stream()
                .map(course -> {
                    CourseVo vo = new CourseVo();
                    vo.setCourseId(course.getCourseId());
                    vo.setName(course.getname());
                    vo.setTeacher(course.getteacher());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseVo> search(CourseSearchDto dto) {
        List<Course> courses = courseMapper.searchCourses(dto);
        return courses.stream()
                .map(course -> {
                    CourseVo vo = new CourseVo();
                    vo.setCourseId(course.getCourseId());
                    vo.setName(course.getname());
                    vo.setTeacher(course.getteacher());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseVo> getOneCourse(Integer selectId) {
        List<Course> courses = courseMapper.getOneCourse(selectId);
        return courses.stream()
                .map(course -> {
                    CourseVo vo = new CourseVo();
                    vo.setCourseId(course.getCourseId());
                    vo.setName(course.getname());
                    vo.setTeacher(course.getteacher());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void delcourse(int courseId, int stuId) {
        boolean success = courseSelectionMapper.cancelCourse(courseId,stuId);
        if (success == false){
            throw new BusinessException(400,"删除失败");
        }
    }
}

