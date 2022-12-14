package com.lyf.ossservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/6 8:18
 * @Modified By:
 */
//当项目已启动，spring接口，spring加载之后，执行接口一个方法
@Component
public class ConstantPropertiesUtil   implements InitializingBean {


    // 读取配置文件内容
    // 隐藏endpoint
    @Value("${endpoint}")
    private String endpoint;



    // @Value("${keyid}")
    private String keyId;

    // @Value("$keysecret}")
    private String keySecret;


    // @Value("${bucketname}")
    private String bucketName;

    //定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
