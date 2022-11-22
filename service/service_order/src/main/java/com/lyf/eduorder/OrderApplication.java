package com.lyf.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: Liang YiFeng
 * @Date: Created in 2022/10/24 9:01
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.lyf"})
@MapperScan("com.lyf.eduorder.mapper")
@EnableDiscoveryClient  //nacos注册
@EnableFeignClients    //服务调用
public class OrderApplication {


    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
