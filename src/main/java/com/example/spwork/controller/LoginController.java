package com.example.spwork.controller;

import com.example.spwork.config.JwtUtil;
import com.example.spwork.dto.StudentInsertDto;
import com.example.spwork.dto.StudentLoginDto;
import com.example.spwork.entity.Response;
import com.example.spwork.service.StudentService;
import com.example.spwork.vo.LoginVo;
import com.example.spwork.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class LoginController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 注册
     */
    @PostMapping("student/insert")
    public ResponseEntity<Response<StudentVo>> insertStudent(@Valid@RequestBody StudentInsertDto dto) {
        StudentVo vo = studentService.insert(dto);
        return ResponseEntity.ok(Response.success(vo));
    }

    /**
     * 登录：校验密码后签发 JWT 令牌
     */
    @PostMapping("student/login")
    public ResponseEntity<Response<LoginVo>> login(@Valid @RequestBody StudentLoginDto dto) {
        StudentVo vo = studentService.login(dto);
        // 生成令牌，Payload 只存非敏感信息
        String token = jwtUtil.generateToken(vo.getStuId(), vo.getStuNo(), vo.getStuName());

        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setStuId(vo.getStuId());
        loginVo.setStuNo(vo.getStuNo());
        loginVo.setStuName(vo.getStuName());
        return ResponseEntity.ok(Response.success(loginVo));
    }
}
