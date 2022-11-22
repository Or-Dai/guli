package com.lyf.ossservice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/6 8:25
 * @Modified By:
 */
public interface OssService {
    public String uploadFileAvatar(MultipartFile file);

    //工具类获取值

}
