package com.lyf.eduservice.client;

import com.lyf.cmsservice.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/7/16 11:35
 * @Modified By:
 */

@FeignClient(name = "service-vod" , fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    /**
     * 根据视频id删除阿里云视频
     * @param id
     * @return
     */
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") String id);

    /**
     * 删除多个阿里云视频的方法
     * 参数多个视频ID
     */
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}


