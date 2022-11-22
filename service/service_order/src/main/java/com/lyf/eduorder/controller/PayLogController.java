package com.lyf.eduorder.controller;


import com.lyf.cmsservice.R;
import com.lyf.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-10-24
 */
@RestController
@RequestMapping("/eduorder/pay-log")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;


    /**
     * 生成微信支付二维码接口
     * @param orderNo   订单号
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }


    /**
     * 查询订单支付状态
     * @param orderNo   订单号
     * @return
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        if (map.get("trade_state").equals("SUCCESS")) {
            //更改订单状态
            payLogService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }

        return R.ok().message("支付中");
    }

}

