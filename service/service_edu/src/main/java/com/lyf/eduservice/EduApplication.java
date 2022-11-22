package com.lyf.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/2 10:11
 * @Modified By:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lyf"})
// 注册Nacos
@EnableDiscoveryClient
// 注册Feign
@EnableFeignClients
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
