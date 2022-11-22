package com.lyf.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.cmsservice.R;
import com.lyf.eduservice.entity.EduCourse;
import com.lyf.eduservice.entity.EduTeacher;
import com.lyf.eduservice.service.EduCourseService;
import com.lyf.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/7/22 16:50
 * @Modified By:
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;


    @Autowired
    private EduCourseService courseService;


    /**
     * 分页查询接口
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit){
        Page<EduTeacher> pageTeacher =  new Page<>(page,limit);
        Map<String,Object> map  = teacherService.getTeacherFrontList(pageTeacher);


        // 返回分页所有数据
        return R.ok().data(map);
    }


    /**
     * 获取详情讲师信息
     * @param teacherId
     * @return
     */
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId){

        // 1.根据讲师id 查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);

        // 2. 根据讲师id,查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }


}
