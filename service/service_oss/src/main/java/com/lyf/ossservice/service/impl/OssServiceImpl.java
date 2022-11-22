package com.lyf.ossservice.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.lyf.ossservice.service.OssService;
import com.lyf.ossservice.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/6 8:25
 * @Modified By:
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;


        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            // 获取上传文件流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //1. 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replace("-","");
            //jreig485701.jpg
            fileName  = uuid+fileName;


            //2. 把文件按照日期进行分类
            // 2022/3/4/01.jpg
            //获取当前日期

            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            fileName = datePath +"/"+fileName;


            //调用oss方法实现上传
            //第一个参数 Bucket名称
            //第二个参数 上传到oss文件路径和文件名称 /aa/bb/1.jpg
            //第三个参数 上传文件输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            //关闭OssClient
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            // https://edu-guli0.oss-cn-beijing.aliyuncs.com/01.jpg
            String url= "https://"+bucketName+"."+endPoint+"/"+fileName;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
