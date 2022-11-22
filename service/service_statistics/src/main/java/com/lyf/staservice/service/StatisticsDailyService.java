package com.lyf.staservice.service;

import com.lyf.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author lyf
 * @since 2022-11-16
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String day);

    /**
     * 图表显示
     * @param begin
     * @param end
     * @param type
     * @return  日期json 数组 和数量json数组
     */
    Map<String, Object> getShowData(String begin, String end, String type);
}
