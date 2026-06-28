package com.example.spwork.controller;

import com.example.spwork.dto.CourseInsertDto;
import com.example.spwork.dto.CourseSearchDto;
import com.example.spwork.dto.StudentUpdataDto;
import com.example.spwork.entity.Course;
import com.example.spwork.entity.CourseSelection;
import com.example.spwork.entity.Response;
import com.example.spwork.entity.Student;
import com.example.spwork.service.CourseSelectionService;
import com.example.spwork.service.CourseService;
import com.example.spwork.service.StudentService;
import com.example.spwork.vo.CourseVo;
import com.example.spwork.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
@CrossOrigin(origins = "http://localhost:8080")
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
     * @return
     */
    @GetMapping("course/list")
    public Response<List<CourseVo>> getAllCourses() {
        List<CourseVo> courses = courseService.getAllCourses();
        return Response.success(courses);
    }

    /**
     * 选课
     * @param dto
     * @return
     */
    @PostMapping("courseselection/insert")
    public ResponseEntity<Response<?>> insertCourseSelection(@Valid @RequestBody CourseInsertDto dto) {
        courseSelectionService.insertCS(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.success("选课成功"));
    }

    /**
     * 修改个人信息
     * @param dto
     * @return
     */
    @PostMapping("student/update")
    //@Transactional(Exception =)
    public Response<StudentVo> updateStudent(@Valid@RequestBody StudentUpdataDto dto) {
        StudentVo vo2 = studentService.updata(dto);
        return Response.success(vo2);
    }

    /**
     * 模糊查询课程
     * @param dto
     * @return
     */
    @GetMapping("course/search")
    public Response<List<CourseVo>> searchCourses(CourseSearchDto dto){
        List<CourseVo> courseVos = courseService.search(dto);
        return Response.success(courseVos);
    }

}

