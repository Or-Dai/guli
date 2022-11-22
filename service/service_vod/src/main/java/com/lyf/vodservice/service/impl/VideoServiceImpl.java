package com.lyf.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.lyf.cmsservice.exception.GuliException;
import com.lyf.vodservice.service.VideoService;
import com.lyf.vodservice.utils.AliyunVodSDKUtils;
import com.lyf.vodservice.utils.ConstantPropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/21 20:00
 * @Modified By:
 */
@Service
@Slf4j
public class VideoServiceImpl implements VideoService {

    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            // 上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 上传文件原始名称
            String originalFilename = file.getOriginalFilename();
            // 上传文件显示名称
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                log.warn(errorMessage);
                if(StringUtils.hasText(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }
    }


    /**
     * 删除阿里云端视频
     * @param id
     */
    @Override
    public void removeVideo(String id) {
        try {
            //初初始对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            DeleteVideoResponse response  = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    /**
     * 删除多个视频
     * @param videoIdList
     */
    @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            //初初始对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            //videoList 值转换成1,2,3
            String videoIds = org.apache.commons.lang.StringUtils.join(videoIdList.toArray(), ",");
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            DeleteVideoResponse response  = client.getAcsResponse(request);

            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        String join = org.apache.commons.lang.StringUtils.join(list.toArray(), ",");
        System.out.println(join);
    }
}
