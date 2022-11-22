package com.lyf.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyf.cmsservice.JwtUtils;
import com.lyf.cmsservice.R;
import com.lyf.eduorder.entity.Order;
import com.lyf.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-10-24
 */
@RestController
@CrossOrigin
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 生成订单
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId,memberId);
        return R.ok().data("orderId",orderNo);
    }

    /**
     * 根据的订单id查询订单信息
     * @param orderId
     * @return
     */
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    /**
     * 根据课程id和用户id查询：订单表中订单状态
     * @param courseId
     * @param memberId
     * @return
     */
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId, @PathVariable String memberId){

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        // 支付状态：1(代表已支付)
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count > 0){
            return true;
        }else{
            return false;
        }
    }

}

