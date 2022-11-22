package com.lyf.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/7/17 20:15
 * @Modified By:
 */
@SpringBootApplication
@ComponentScan({"com.lyf"}) //指定扫描位置
@MapperScan("com.lyf.cmsservice.mapper")
@EnableDiscoveryClient  //Nacos注册
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
