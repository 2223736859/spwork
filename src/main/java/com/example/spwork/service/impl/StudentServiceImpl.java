package com.example.spwork.service.impl;

import com.example.spwork.dto.StudentInsertDto;
import com.example.spwork.dto.StudentLoginDto;
import com.example.spwork.dto.StudentUpdataDto;
import com.example.spwork.entity.Student;
import com.example.spwork.exception.BusinessException;
import com.example.spwork.mapper.StudentMapper;
import com.example.spwork.service.StudentService;
import com.example.spwork.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Override
    public StudentVo login(StudentLoginDto dto){
        Student student = studentMapper.selectByNo(dto.getStuNo());
        if (student == null) {
            throw new BusinessException(400, "账号或密码错误");
        }
        if (!dto.getStuPsd().equals(student.getStuPsd())){
            throw new BusinessException(400,"账号密码错误");
        }
            //转化为VO
            StudentVo vo = new StudentVo();
            vo.setStuId(student.getStuId());
            vo.setStuNo(student.getStuNo());
            vo.setStuName(student.getStuName());
            return vo;
    }

    public StudentVo insert(StudentInsertDto dto){
        //是否已经存在
        Student test = studentMapper.selectByNo(dto.getStuNo());
        if (test != null){
            throw new BusinessException(400,"账号已存在");
        }

        //DTO转entity
        Student student = new Student();
        student.setStuName(dto.getStuName());
        student.setStuNo(dto.getStuNo());
        student.setStuPsd(dto.getStuPsd());

        //插入数据库
        studentMapper.insertStudent(student);

        //entity转VO
        StudentVo vo = new StudentVo();
        vo.setStuId(student.getStuId());
        vo.setStuName(student.getStuName());
        vo.setStuNo(student.getStuNo());

        return vo;
    }

    @Override
    public StudentVo updata(StudentUpdataDto dto) {
        Student extion = studentMapper.selectByNo(dto.getStuNo());
        if (extion == null){
            throw new BusinessException(400,"账号不存在");
        }

        Student student = new Student();
        student.setStuId(dto.getStuId());
        student.setStuPsd(dto.getStuPsd());
        student.setStuName(dto.getStuName());
        student.setStuNo(dto.getStuNo());

        studentMapper.updateStudent(student);

        Student updated = studentMapper.selectByNo(dto.getStuNo());
        StudentVo vo = new StudentVo();
        vo.setStuNo(dto.getStuNo());
        vo.setStuName(dto.getStuName());
        vo.setStuId(dto.getStuId());

        return vo;
    }
}
