package com.lyf.staservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyf.cmsservice.R;
import com.lyf.staservice.client.UcenterClient;
import com.lyf.staservice.entity.StatisticsDaily;
import com.lyf.staservice.mapper.StatisticsDailyMapper;
import com.lyf.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-11-16
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {

        // 添加记录之前删除表相同日期的数据

//        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
//        wrapper.eq("date_calculated",day);
//        baseMapper.delete(wrapper);


        // 远程调用得到某一天注册人数
        R register = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) register.getData().get("countRegister");



        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        // 注册人数
        daily.setRegisterNum(countRegister);

        daily.setLoginNum(RandomUtils.nextInt(100, 200));
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));
        // 课程数量
        daily.setCourseNum(RandomUtils.nextInt(100, 200));
        // 统计日期
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    /**
     * 图表显示
     * @param begin
     * @param end
     * @param type
     * @return  日期json 数组 和数量json数组
     */

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);
        //查询需要的列，数据时间和类型
        queryWrapper.select("date_calculated",type);
        List<StatisticsDaily> lists = baseMapper.selectList(queryWrapper);

        //返回的数据有两部分：date_calculated     和      所类型type
        //前端需要json数据，对于java代码的list集合，需要返回x、y轴两个list数据
        ArrayList<String> date_calculatedList = new ArrayList<>();
        ArrayList<Integer> numDataList = new ArrayList<>();
        //遍历lists封装两个list集合
        for (StatisticsDaily list:lists){
            //封装日期
            date_calculatedList.add(list.getDateCalculated());

            //封装数量类型
            if (type.equals("login_num")){
                numDataList.add(list.getLoginNum());
            }
            if (type.equals("register_num")){
                numDataList.add(list.getRegisterNum());
            }
            if (type.equals("video_view_num")){
                numDataList.add(list.getVideoViewNum());
            }
            if (type.equals("course_num")){
                numDataList.add(list.getCourseNum());
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
