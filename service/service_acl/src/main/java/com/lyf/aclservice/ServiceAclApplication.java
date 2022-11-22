package com.lyf.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Liang YiFeng
 * @Date: Created in 2022/11/19 9:07
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.lyf")
@MapperScan("com.lyf.aclservice.mapper")
public class ServiceAclApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAclApplication.class, args);
    }

}
