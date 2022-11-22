package com.lyf.eduorder.service;

import com.lyf.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author lyf
 * @since 2022-10-24
 */
public interface OrderService extends IService<Order> {

    /**
     *
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    String createOrders(String courseId, String memberIdByJwtToken);
}
