package com.lyf.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.eduservice.entity.EduCourseDescription;
import com.lyf.eduservice.entity.vo.*;
import com.lyf.eduservice.entity.EduCourse;
import com.lyf.eduservice.mapper.EduCourseMapper;
import com.lyf.eduservice.service.EduChapterService;
import com.lyf.eduservice.service.EduCourseDescriptionService;
import com.lyf.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyf.eduservice.service.EduVideoService;
import com.lyf.cmsservice.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //注入章节
    @Autowired
    private EduChapterService chapterService;

    //注入小节
    @Autowired
    private EduVideoService videoService;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
         // 1. 向课程表添加课程基本信息
        // CourseInfoVo 对象转换为 eduCourse对象
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if (insert ==0){
            //添加失败
            throw new GuliException(20001,"添加课程信息失败");
        }

        //获取添加之后课程Id
        String cid = eduCourse.getId();



        // 2. 向课程简介表添加课程简介
        //edu_course_description
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述Id就是课程Id
        courseDescription.setId(cid);
        courseDescriptionService.save(courseDescription);
        return cid;
    }

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //1.查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2.查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    /**
     * //修改课程信息
     * @param courseInfoVo
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1.修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update ==0){
            throw  new GuliException(20001,"修改课程信息失败");
        }


        //2.修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);

    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    /**
     * 删除课程
     * @param courseId
     */
    @Override
    public void removeCourse(String courseId) {
        //1.根据课程Id删除小节
        videoService.removeVideoByCourseId(courseId);


        //2.根据课程Id删除章节
        chapterService.removeChapterByCourseId(courseId);


        // 3.根据课程id 删除描述
        courseDescriptionService.removeById(courseId);

        //4. 根据课程Id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if (result == 0){ //失败返回
            throw new GuliException(20001,"删除失败");
        }

    }

    /**
     *  分页查询课程前台
     *
     * @param pageParam
     * @param courseQuery
     */
    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {

        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        Integer level = courseQuery.getLevel();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();


        if (StringUtils.hasText(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.hasLength(String.valueOf(level)) ) {
            queryWrapper.eq("level", level);
        }

        if (StringUtils.hasText(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (StringUtils.hasText(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (StringUtils.hasText(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {
        // 2. 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());// 一级分类
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            wrapper.eq("subject_id", courseFrontVo.getSubjectId());  //二级分类
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");  //关注度
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create"); //最新
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            wrapper.orderByDesc("price"); //价格
        }
        baseMapper.selectPage(pageParam, wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     * 根据课程ID，编写sql语句查询课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
