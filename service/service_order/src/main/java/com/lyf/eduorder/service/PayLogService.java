package com.lyf.eduorder.service;

import com.lyf.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author lyf
 * @since 2022-10-24
 */
public interface PayLogService extends IService<PayLog> {

    Map createNative(String orderNo);

    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 更新订单状态
     * @param map
     */
    void updateOrdersStatus(Map<String, String> map);
}
