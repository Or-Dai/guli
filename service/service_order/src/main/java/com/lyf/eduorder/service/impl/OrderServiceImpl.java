package com.lyf.eduorder.service.impl;

import com.lyf.cmsservice.ordervo.CourseWebVoOrder;
import com.lyf.cmsservice.ordervo.UcenterMemberOrder;
import com.lyf.eduorder.client.EduClient;
import com.lyf.eduorder.client.UcenterClient;
import com.lyf.eduorder.entity.Order;
import com.lyf.eduorder.mapper.OrderMapper;
import com.lyf.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyf.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-10-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private EduClient eduClient;

    @Override
    public String createOrders(String courseId, String memberId) {

        // 通过远程调用根据课程id获取用户信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);


        // 通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);


        // 创建Order对象，向order对象里面设置需要数据
        Order order = new Order();
        // 使用工具类生成：订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        // 课程Id
        order.setCourseId(courseId);
        // 课程标题
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        // 讲师名称
        order.setTeacherName(courseInfoOrder.getTeacherName());
        // 课程价格
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        // 支付状态 订单状态：0:未支付 1: 已支付
        order.setStatus(1);
        // 支付类型，微信1 ，支付宝2
        order.setPayType(1);
        baseMapper.insert(order);

        // 返回订单号
        return order.getOrderNo();
    }
}
