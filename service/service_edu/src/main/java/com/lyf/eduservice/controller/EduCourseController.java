package com.lyf.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.cmsservice.R;
import com.lyf.eduservice.entity.vo.CoursePublishVo;
import com.lyf.eduservice.entity.EduCourse;
import com.lyf.eduservice.entity.vo.CourseInfoVo;
import com.lyf.eduservice.entity.vo.CourseQuery;
import com.lyf.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;



    //添加课程基本信息的方法
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后课程Id,为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }


    /**
     * 修改课程信息
     * @param courseInfoVo
     * @return
     */
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);

        return R.ok();
    }

    /**
     * 根据课程Id查询课程确认信息
     * @param id
     * @return
     */
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return  R.ok().data("publishCourse",coursePublishVo);
    }

    /**
     * 课程最终发布：修改课程状态
     * @param id
     * @return
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal"); //设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }


    /**
     * 课程列表 基本实现
     * TODO 完善条件查询分页
     * @return
     */
    @GetMapping("/page")
    public R getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }


    /**
     *  分页查询课程的方法
     * @param pageCurrent :当前页码
     * @param limit :每页记录数
     * @return
     */
    @ApiOperation(value = "分页课程列表")
    @GetMapping("/pageCourse/{pageCurrent}/{limit}")
    public R pageQuery(
            @ApiParam(name = "pageCurrent", value = "当前页码", required = true)
            @PathVariable Long pageCurrent,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        // 创建page对象
        Page<EduCourse> pageParam = new Page<>(pageCurrent, limit);
        // 调用方法实现分页
        // 调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        courseService.page(pageParam, null);
        List<EduCourse> records = pageParam.getRecords(); // 数据list集合
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }


    /**
     * 条件查询带分页的方法
     * @param pageCurrent
     * @param limit
     * @param courseQuery
     * @return
     */
    @ApiOperation(value = "分页课程列表-条件查询")
    @PostMapping("/pageCourseCondition/{pageCurrent}/{limit}")
    public R pageCourseCondition(
            @ApiParam(name = "pageCurrent", value = "当前页码", required = true)
            @PathVariable Long pageCurrent,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    CourseQuery courseQuery){
        // 创建page对象
        Page<EduCourse> pageParam = new Page<>(pageCurrent, limit);

        // 调用方法实现条件查询分页
        courseService.pageQuery(pageParam, courseQuery);

        //数据list集合
        List<EduCourse> records = pageParam.getRecords();

        //获取总记录数
        long total = pageParam.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }


    /**
     * 删除课程
     * @param courseId
     * @return
     */
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }



}

