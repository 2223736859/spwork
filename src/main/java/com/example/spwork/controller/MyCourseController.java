package com.example.spwork.controller;

import com.example.spwork.entity.Course;
import com.example.spwork.entity.CourseSelection;
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
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class MyCourseController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseSelectionService courseSelectionService;

    /**
     * 查询个人已选课程
     * @param selectId
     * @return
     */
    @GetMapping("/courseselection/list")
    public Response<List<CourseVo>> getCourses(@RequestParam("selectId") Integer selectId) {
        List<CourseVo> courseVos = courseService.getOneCourse(selectId);
        return Response.success(courseVos);
    }

    /**
     * 删除已选课程
     * @param courseId
     * @param stuId
     * @return
     */
    @DeleteMapping("/courseselection/cancel/{courseId}")
    public ResponseEntity<Response<?>> cancelCourse(@PathVariable("courseId") int courseId, @RequestParam("stuId") int stuId) {
        courseService.delcourse(courseId,stuId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.success("删除成功"));
    }

}

