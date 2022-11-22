package com.lyf.staservice.controller;


import com.lyf.cmsservice.R;
import com.lyf.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-11-16
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    /**
     * 统计某一天注册人数，生成统计数据
     * @param day
     * @return
     */
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        staService.registerCount(day);
        return R.ok();
    }


    /**
     * 图表显示
     * @param type
     * @param begin
     * @param end
     * @return  日期json 数组 和数量json数组
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showChart(@PathVariable String type,@PathVariable String begin,@PathVariable String end){
        Map<String, Object> map = staService.getShowData(type,begin, end);
        return R.ok().data(map);
    }
}

