package com.example.spwork.mapper;

import com.example.spwork.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {
    int insertStudent(Student student);

    Student getByUserNameAndPassword(Student student);

    Student selectByNo(String stuNo);

    /**
     * 按主键 stuId 查询学生（用于从 Token 取 stuId 后查询）
     */
    Student selectById(Integer stuId);

    int updateStudent(Student student);
}
