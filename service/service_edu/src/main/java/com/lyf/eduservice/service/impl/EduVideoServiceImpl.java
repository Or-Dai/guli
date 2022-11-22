package com.lyf.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyf.eduservice.client.VodClient;
import com.lyf.eduservice.entity.EduVideo;
import com.lyf.eduservice.mapper.EduVideoMapper;
import com.lyf.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    //注入Vod
    @Autowired
    private VodClient vodClient;

    /**
     * 根据课程Id删除小节
     * TODO 删除小节，删除对应视频文件
     * @param courseId
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        // 1.根据课程ID查询课程所有的视频ID
        QueryWrapper<EduVideo>  wrappeVideo = new QueryWrapper<>();
        wrappeVideo.eq("course_id",courseId);
        wrappeVideo.select("video_source_id");
        List<EduVideo> eduVideosList = baseMapper.selectList(wrappeVideo);

        // list <Eduvideo>变成List<String>
        List<String> videoIds = new ArrayList<>();
        for (int i = 0; i< eduVideosList.size(); i++){
            EduVideo eduVideo = eduVideosList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            //放到videoIds集合里面
            videoIds.add(videoSourceId);
        }
        //根据多个视频id删除多个视频
        if(videoIds.size()>0){
            vodClient.deleteBatch(videoIds);
        }




        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }


}
