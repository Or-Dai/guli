package com.lyf.eduorder.client;


import com.lyf.cmsservice.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: Liang YiFeng
 * @Date: Created in 2022/10/24 10:54
 * @Description:
 */

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据用户id获取客户信息
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id);
}
