package com.lyf.eduorder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Author: Liang YiFeng
 * @Date: Created in 2022/10/24 11:03
 * @Description:订单号工具类
 */
public class OrderNoUtil {

    public static String getOrderNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }
}
