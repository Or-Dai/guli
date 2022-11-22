package com.lyf.ossservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/6 8:47
 * @Modified By:
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class )
@ComponentScan(basePackages = {"com.lyf"})
@EnableDiscoveryClient  //Nacos注册
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
