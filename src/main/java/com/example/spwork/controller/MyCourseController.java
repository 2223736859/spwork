package com.example.spwork.controller;

import com.example.spwork.context.UserContext;
import com.example.spwork.entity.Response;
import com.example.spwork.service.CourseSelectionService;
import com.example.spwork.service.CourseService;
import com.example.spwork.service.StudentService;
import com.example.spwork.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyCourseController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseSelectionService courseSelectionService;

    /**
     * 查询个人已选课程：stuId 从 JWT Token 中获取，用户无法查看别人的选课
     */
    @GetMapping("/courseselection/list")
    public Response<List<CourseVo>> getCourses() {
        Integer stuId = UserContext.getStuId();
        List<CourseVo> courseVos = courseService.getOneCourse(stuId);
        return Response.success(courseVos);
    }

    /**
     * 删除已选课程（退课）：stuId 从 JWT Token 中获取，防止越权退别人的课
     */
    @DeleteMapping("/courseselection/cancel/{courseId}")
    public ResponseEntity<Response<?>> cancelCourse(@PathVariable("courseId") int courseId) {
        Integer stuId = UserContext.getStuId();
        courseService.delcourse(courseId, stuId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("删除成功"));
    }
}
