package com.example.spwork.mapper;

import com.example.spwork.entity.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {
    int insertStudent(Student student);

    Student getByUserNameAndPassword(Student student);

    Student selectByNo(String stuNo);

    int updateStudent(Student student);
}