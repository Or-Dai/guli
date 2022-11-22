package com.lyf.gataway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Liang YiFeng
 * @Date: Created in 2022/11/18 14:54
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient  //服务发现
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class);
    }
}
