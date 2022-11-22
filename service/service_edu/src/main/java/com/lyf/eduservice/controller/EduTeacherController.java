package com.lyf.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.cmsservice.R;
import com.lyf.eduservice.entity.EduTeacher;
import com.lyf.eduservice.entity.vo.TeacherQuery;
import com.lyf.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-03-02
 */

//@CrossOrigin //跨域
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //注入Service
    @Autowired
    private EduTeacherService teacherService;





    /*
    */
/**
     * 查询所有讲师——没有统一Json
     * @return
     *//*

    @GetMapping("/findAll")
    public List<EduTeacher> list(){
        return  teacherService.list(null); //wrapper:传入条件
    }

*/

    /**
     * 查询所有讲师——返回统一Json结果集
     * @return
     */
    @ApiOperation(value = "查询所有讲师")
    @GetMapping("/findAll")
    public R findAll(){
        List<EduTeacher> list = teacherService.list(null);
//        int i=10/0;
/*        try {
            int a = 10/0;
        }catch(Exception e) {
            throw new GuliException(20001,"出现自定义异常");
        }*/
        return R.ok().data("items", list);
    }

/*
    */
/**
     * 逻辑删除讲师
     * @param id
     * @return
     *//*

    @DeleteMapping("/delete/{id}")
    public boolean removeById(@PathVariable String id){
        return teacherService.removeById(id);
    }

*/

    /**
     * 逻辑删除讲师——返回统一Json结果集
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("/delete/{id}")
    public R removeById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        }else{
            return R.error();
        }
    }


    /**
     *  分页查询讲师的方法
     * @param pageCurrent :当前页码
     * @param limit :每页记录数
     * @return
     */
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("/pageTeacher/{pageCurrent}/{limit}")
    public R pageList(
            @ApiParam(name = "pageCurrent", value = "当前页码", required = true)
            @PathVariable Long pageCurrent,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        // 创建page对象
        Page<EduTeacher> pageParam = new Page<>(pageCurrent, limit);
        // 调用方法实现分页
        // 调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        teacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords(); // 数据list集合
        long total = pageParam.getTotal();
        return  R.ok().data("total", total).data("rows", records);
    }


    /**
     *  条件查询带分页的方法
     * @param pageCurrent
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "分页讲师列表-条件查询")
    @PostMapping("pageTeacherCondition/{pageCurrent}/{limit}")
    public R pageTeacherCondition(@PathVariable Long pageCurrent,
                                  @PathVariable Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建Page对象
        Page<EduTeacher> pageParam = new Page<>(pageCurrent, limit);


        // 调用方法实现条件查询分页
        teacherService.pageQuery(pageParam, teacherQuery);

        //数据list集合
        List<EduTeacher> records = pageParam.getRecords();

        //获取总记录数
        long total = pageParam.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);
    }


    /**
     * 新增讲师
     * @param teacher
     * @return
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("/addTeacher")
    public R save(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){

        teacherService.save(teacher);
        return R.ok();
    }


    @ApiOperation(value = "修改讲师")
    @PostMapping("/updateTeacher")
    public R updateById(

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){

        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }



    /**
     * 根据ID查询讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

}

