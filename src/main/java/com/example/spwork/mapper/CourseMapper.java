package com.example.spwork.mapper;

import com.example.spwork.dto.CourseSearchDto;
import com.example.spwork.entity.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseMapper {
    List<Course> getAllCourses();

    List<Course> getOneCourse(int selectId);

    List<Course> searchCourses(@Param("dto")CourseSearchDto dto);
}
