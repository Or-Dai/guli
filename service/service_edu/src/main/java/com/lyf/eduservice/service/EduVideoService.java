package com.lyf.eduservice.service;

import com.lyf.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程Id删除小节
    void removeVideoByCourseId(String courseId);
}
