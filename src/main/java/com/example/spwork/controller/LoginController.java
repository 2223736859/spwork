package com.example.spwork.controller;

import com.example.spwork.dto.StudentInsertDto;
import com.example.spwork.dto.StudentLoginDto;
import com.example.spwork.entity.Response;
import com.example.spwork.service.StudentService;
import com.example.spwork.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class LoginController {
    @Autowired
    private StudentService studentService;

    /**
     * 注册
     * @param dto
     * @return
     */
    @PostMapping("student/insert")
    public ResponseEntity<Response<StudentVo>> insertStudent(@Valid@RequestBody StudentInsertDto dto) {
        StudentVo vo = studentService.insert(dto);
        return ResponseEntity.ok(Response.success(vo));
    }

    /**
     * 登录
     * @param dto
     * @return
     */
    @PostMapping("student/login")
    public ResponseEntity<Response<StudentVo>> login(@Valid @RequestBody StudentLoginDto dto) {

        StudentVo vo =studentService.login(dto);
        return ResponseEntity.ok(Response.success(vo));
    }


}