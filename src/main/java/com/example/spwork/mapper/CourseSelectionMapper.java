package com.example.spwork.mapper;

import com.example.spwork.entity.CourseSelection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSelectionMapper {
    int insertCS(@Param("courseId")Integer courseId,@Param("stuId") Integer stuId);

    CourseSelection selectById(@Param("courseId")Integer courseId,@Param("stuId") Integer stuId);

    boolean cancelCourse(@Param("courseId")Integer courseId,@Param("stuId") Integer stuId);
}
