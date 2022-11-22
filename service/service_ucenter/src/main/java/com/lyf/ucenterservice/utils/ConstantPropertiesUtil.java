package com.lyf.ucenterservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/7/21 18:40
 * @Modified By:
 */
@Component
//@PropertySource("classpath:application.properties")
public class ConstantPropertiesUtil  implements InitializingBean {

    // 隐藏腾讯云秘钥:wx.open.app_id
    @Value("${app_id}")
    private String appId;

    @Value("${app_secret}")
    private String appSecret;

    @Value("${redirect_url}")
    private String redirectUrl;

    public static String WX_OPEN_APP_ID;
    public static String WX_OPEN_APP_SECRET;
    public static String WX_OPEN_REDIRECT_URL;


    @Override
    public void afterPropertiesSet() throws Exception {
        WX_OPEN_APP_ID = appId;
        WX_OPEN_APP_SECRET = appSecret;
        WX_OPEN_REDIRECT_URL = redirectUrl;
    }
}
