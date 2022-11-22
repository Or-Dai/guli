package com.lyf.eduservice.mapper;


import com.lyf.eduservice.entity.vo.CoursePublishVo;
import com.lyf.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyf.eduservice.entity.vo.CourseWebVo;


/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);


    //根据课程ID，编写sql语句查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
