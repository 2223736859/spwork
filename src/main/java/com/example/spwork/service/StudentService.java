package com.example.spwork.service;

import com.example.spwork.dto.StudentInsertDto;
import com.example.spwork.dto.StudentLoginDto;
import com.example.spwork.dto.StudentUpdataDto;
import com.example.spwork.vo.StudentVo;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    StudentVo login(StudentLoginDto dto);

    StudentVo insert(StudentInsertDto dto);

    StudentVo updata(StudentUpdataDto dto);
}