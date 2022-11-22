package com.lyf.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.eduservice.entity.EduTeacher;
import com.lyf.eduservice.entity.vo.TeacherQuery;
import com.lyf.eduservice.mapper.EduTeacherMapper;
import com.lyf.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-03-02
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    /**
     * 分页查询
     * @param pageParam
     * @param teacherQuery
     */
    @Override
    public void pageQuery(Page<EduTeacher> pageParam , TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if (teacherQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 判断name是否为空，则模糊查询
        // hasText判断这个对象是否未赋值、是否为空、是否该字符串包含空格-则去掉。最后返回该字符串
        if (StringUtils.hasText(name)) {
            queryWrapper.like("name", name);
        }

        // 判断等级是否为空，则判断是否等价于
        // String.valueOf判断这个对象是否为空，否则返回对象的字符串
        // 加上! 若等于空，则不执行该if语句
        // 若不加! 则判断level=null , 由于我们level只有0、1、2,则会查询不到数据
        if (!StringUtils.hasLength(String.valueOf(level)) ) {
            queryWrapper.eq("level", level);
        }

        // 判断最早创建时间是否为空，则判断是否大于
        if (StringUtils.hasText(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        // 判断最后创建时间为空，则判断是否小于
        if (StringUtils.hasText(end)) {
            queryWrapper.le("gmt_create", end);
        }
        baseMapper.selectPage(pageParam, queryWrapper);


    }

    /**
     * 获取教师列表页
     * @param pageParam
     * @return
     */
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象
        baseMapper.selectPage(pageParam , wrapper);

        List<EduTeacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();  // 下一页
        boolean hasPrevious = pageParam.hasPrevious(); // 上一页

        // 把分页数据获取出阿里，放到map集合
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }


}
