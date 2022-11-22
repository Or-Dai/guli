package com.lyf.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.lyf.cmsservice.R;
import com.lyf.cmsservice.exception.GuliException;
import com.lyf.vodservice.service.VideoService;
import com.lyf.vodservice.utils.AliyunVodSDKUtils;
import com.lyf.vodservice.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/21 19:55
 * @Modified By:
 */

@Api(tags="阿里云视频点播微服务")
@RestController
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VideoService videoService;

    /**
     * 上传视频文件
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {

        String videoId = videoService.uploadVideo(file);
        return R.ok().data("videoId", videoId);
    }


    /**
     * 根据视频id删除阿里云视频
     * @param id
     * @return
     */
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@ApiParam(name = "id", value = "阿里云端视频id", required = true)
                                @PathVariable String id) {
        videoService.removeVideo(id);
        return R.ok().message("视频删除成功");
    }


    /**
     * 删除多个阿里云视频的方法
     * 参数多个视频ID
     */
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        videoService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }


    /**
     * 根据视频id获取视频凭证
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("getPlayAuth/{id}")
    public R getVideoPlayAuth(@PathVariable("id") String id) throws Exception {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        try{
            //创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
            //创建获取凭证request   和 response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            // 向request设置视频id
            request.setVideoId(id);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();

            //返回结果
            return R.ok().message("获取凭证成功").data("playAuth", playAuth);
        }catch (Exception e){
            throw new GuliException(20001,"获取凭证失败");
        }
    }


}
