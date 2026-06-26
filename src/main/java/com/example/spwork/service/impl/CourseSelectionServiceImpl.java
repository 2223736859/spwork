package com.example.spwork.service.impl;

import com.example.spwork.dto.CourseInsertDto;
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
    public void insertCS(CourseInsertDto dto) {
        CourseSelection exist = courseSelectionMapper.selectById(dto.getCourseId(),dto.getStuId());
        if (exist != null){
            throw new BusinessException(400,"你已经选过该课程");
        }

        courseSelectionMapper.insertCS(dto.getCourseId(),dto.getStuId());
    }
}
