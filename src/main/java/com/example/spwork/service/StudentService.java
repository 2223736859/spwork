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

    /**
     * 修改个人信息
     * @param stuId 学生 ID（从 JWT Token 中获取，不信任前端）
     * @param dto   前端传入的修改内容（姓名、学号、新密码）
     */
    StudentVo updata(Integer stuId, StudentUpdataDto dto);
}
