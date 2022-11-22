package com.lyf.eduservice.client;

import com.lyf.cmsservice.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/7/16 23:43
 * @Modified By:
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
