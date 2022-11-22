package com.lyf.eduorder.client;

import com.lyf.cmsservice.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: Liang YiFeng
 * @Date: Created in 2022/10/24 10:54
 * @Description:
 */

@Component
@FeignClient("service-edu")
public interface EduClient {

    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);
}
