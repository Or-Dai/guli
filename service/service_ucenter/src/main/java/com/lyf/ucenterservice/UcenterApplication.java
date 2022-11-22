package com.lyf.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/7/18 17:38
 * @Modified By:
 */
@ComponentScan({"com.lyf"})
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.lyf.ucenterservice.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
