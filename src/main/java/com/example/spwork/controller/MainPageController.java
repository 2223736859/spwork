package com.example.spwork.controller;

import com.example.spwork.context.UserContext;
import com.example.spwork.dto.CourseInsertDto;
import com.example.spwork.dto.CourseSearchDto;
import com.example.spwork.dto.StudentUpdataDto;
import com.example.spwork.entity.Response;
import com.example.spwork.service.CourseSelectionService;
import com.example.spwork.service.CourseService;
import com.example.spwork.service.StudentService;
import com.example.spwork.vo.CourseVo;
import com.example.spwork.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MainPageController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseSelectionService courseSelectionService;

    /**
     * 展示课程
     */
    @GetMapping("course/list")
    public Response<List<CourseVo>> getAllCourses() {
        List<CourseVo> courses = courseService.getAllCourses();
        return Response.success(courses);
    }

    /**
     * 选课：stuId 从 JWT Token 中获取，不信任前端传参（防越权）
     */
    @PostMapping("courseselection/insert")
    public ResponseEntity<Response<?>> insertCourseSelection(@Valid @RequestBody CourseInsertDto dto) {
        Integer stuId = UserContext.getStuId();
        courseSelectionService.insertCS(dto.getCourseId(), stuId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.success("选课成功"));
    }

    /**
     * 修改个人信息：stuId 从 JWT Token 中获取，防止越权修改别人资料
     */
    @PostMapping("student/update")
    public Response<StudentVo> updateStudent(@Valid @RequestBody StudentUpdataDto dto) {
        Integer stuId = UserContext.getStuId();
        StudentVo vo2 = studentService.updata(stuId, dto);
        return Response.success(vo2);
    }

    /**
     * 模糊查询课程
     */
    @GetMapping("course/search")
    public Response<List<CourseVo>> searchCourses(CourseSearchDto dto){
        List<CourseVo> courseVos = courseService.search(dto);
        return Response.success(courseVos);
    }

}
