package com.lyf.ossservice.controller;

import com.lyf.cmsservice.R;
import com.lyf.ossservice.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/6 8:25
 * @Modified By:
 */
@RestController
@RequestMapping("/eduoss/file")
//@CrossOrigin //解决跨域
public class OssController {

    @Autowired
    private OssService ossService;


    //上传头像的方法
    @ApiOperation(value = "上传头像")
    @PostMapping("/upload")
    public R uploadOssFile(MultipartFile file){
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().message("文件上传成功").data("url", url);
    }
}


