package com.lyf.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.cmsservice.JwtUtils;
import com.lyf.cmsservice.R;
import com.lyf.cmsservice.ordervo.CourseWebVoOrder;
import com.lyf.eduservice.client.OrdersClient;
import com.lyf.eduservice.entity.EduCourse;
import com.lyf.eduservice.entity.chapter.ChapterVo;
import com.lyf.eduservice.entity.vo.CourseFrontVo;
import com.lyf.eduservice.entity.vo.CourseWebVo;
import com.lyf.eduservice.service.EduChapterService;
import com.lyf.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/7/17 22:05
 * @Modified By:
 */
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;


    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;


    /**
     * 分页查询课程
     * @param page
     * @param limit
     * @param courseFrontVo
     * @return
     */
    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageParam,courseFrontVo);
        return  R.ok().data(map);
    }


    /**
     * 根据ID查询课程
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId, HttpServletRequest request){

        //根据课程ID，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程ID,查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        // 根据课程id和用户id查询当前课程是否已经支付过
        boolean bucCourse = ordersClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));


        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList).data("isBuy",bucCourse);
    }


    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
