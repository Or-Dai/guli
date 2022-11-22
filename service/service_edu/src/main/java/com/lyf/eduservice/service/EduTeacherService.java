package com.lyf.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyf.eduservice.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author lyf
 * @since 2022-03-02
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //条件查询带分页的方法
    void pageQuery(Page<EduTeacher> pageParam , TeacherQuery teacherQuery);



    //分页查询接口
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);


}
