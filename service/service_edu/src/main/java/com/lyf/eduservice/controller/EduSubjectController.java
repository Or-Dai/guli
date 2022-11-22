package com.lyf.eduservice.controller;


import com.lyf.cmsservice.R;
import com.lyf.eduservice.entity.subject.OneSubject;
import com.lyf.eduservice.service.EduSubjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-03-07
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来

    @ApiOperation(value = "添加课程")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        //上传过来excel文件
        subjectService.saveSubject(file ,subjectService);

        return R.ok();
    }


    @ApiOperation(value = "获取课程列表")
    //课程分类列表（数形）
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        // list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

