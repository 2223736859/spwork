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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentMapper studentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public StudentVo login(StudentLoginDto dto){
        Student student = studentMapper.selectByNo(dto.getStuNo());
        if (student == null) {
            throw new BusinessException(400, "账号或密码错误");
        }
        // 用 BCrypt 校验明文密码与库中密文是否匹配
        if (!passwordEncoder.matches(dto.getStuPsd(), student.getStuPsd())){
            throw new BusinessException(400,"账号或密码错误");
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
        // 密码加密后再入库
        student.setStuPsd(passwordEncoder.encode(dto.getStuPsd()));

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
    public StudentVo updata(Integer stuId, StudentUpdataDto dto) {
        // 用 Token 里的 stuId 按主键查询，不再信任前端传的学号
        Student extion = studentMapper.selectById(stuId);
        if (extion == null){
            throw new BusinessException(400,"账号不存在");
        }

        Student student = new Student();
        student.setStuId(stuId);  // 用 Token 里的 stuId，防止越权修改别人资料
        student.setStuName(dto.getStuName());
        student.setStuNo(dto.getStuNo());
        // 仅当传入新密码时才更新（加密存储），否则保留原密码
        String newPsd = dto.getStuPsd();
        if (newPsd != null && !newPsd.isEmpty()) {
            student.setStuPsd(passwordEncoder.encode(newPsd));
        }

        studentMapper.updateStudent(student);

        StudentVo vo = new StudentVo();
        vo.setStuNo(dto.getStuNo());
        vo.setStuName(dto.getStuName());
        vo.setStuId(stuId);

        return vo;
    }
}
