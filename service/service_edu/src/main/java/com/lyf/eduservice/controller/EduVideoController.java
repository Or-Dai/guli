package com.lyf.eduservice.controller;


import com.lyf.cmsservice.R;
import com.lyf.eduservice.client.VodClient;
import com.lyf.eduservice.entity.EduVideo;
import com.lyf.cmsservice.exception.GuliException;
import com.lyf.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;


    @Autowired
    private VodClient vodClient;


    /**
     * 添加小节
     * @param eduVideo
     * @return
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除小节.删除对应的阿里云视频
     * TODO 这个方法后面需要完善：删除小节时，同时把里面视频删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id查询视频id,调取方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();

        //判断小节里面是否有视频id

        if(!StringUtils.isEmpty(videoSourceId)){
            //根据视频id, 远程调用实现视频删除
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode() == 20001){
                throw new GuliException(20001,"删除视频失败，熔断器....");
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }





}

