package com.example.spwork.service;

import com.example.spwork.dto.CourseInsertDto;
import com.example.spwork.entity.CourseSelection;
import com.example.spwork.mapper.CourseSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface CourseSelectionService {
    void insertCS(CourseInsertDto dto);
}
