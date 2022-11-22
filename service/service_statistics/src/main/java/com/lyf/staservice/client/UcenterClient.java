package com.lyf.staservice.client;

import com.lyf.cmsservice.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Liang YiFeng
 * @Date: Created in 2022/11/16 10:36
 * @Description:
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    @GetMapping("/educenter/member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
