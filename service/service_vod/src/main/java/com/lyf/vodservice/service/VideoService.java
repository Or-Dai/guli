package com.lyf.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/21 19:59
 * @Modified By:
 */
public interface VideoService {
    String uploadVideo(MultipartFile file);


    //删除阿里云端视频
    void removeVideo(String id);

    //删除多个视频
    void removeMoreAlyVideo(List videoIdList);
}
