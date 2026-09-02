package com.example.spwork.service.impl;

import com.example.spwork.entity.CourseSelection;
import com.example.spwork.exception.BusinessException;
import com.example.spwork.mapper.CourseSelectionMapper;
import com.example.spwork.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseSelectionServiceImpl implements CourseSelectionService{

    private final CourseSelectionMapper courseSelectionMapper;
    @Autowired
    public CourseSelectionServiceImpl (CourseSelectionMapper courseSelectionMapper){
        this.courseSelectionMapper = courseSelectionMapper;
    }

    @Override
    public void insertCS(Integer courseId, Integer stuId) {
        // 用 Token 里的 stuId 查重，防止用户篡改前端参数替别人选课
        CourseSelection exist = courseSelectionMapper.selectById(courseId, stuId);
        if (exist != null){
            throw new BusinessException(400,"你已经选过该课程");
        }

        courseSelectionMapper.insertCS(courseId, stuId);
    }
}
