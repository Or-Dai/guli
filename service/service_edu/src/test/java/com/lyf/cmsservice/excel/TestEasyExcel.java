package com.lyf.cmsservice.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/7 11:24
 * @Modified By:
 */
public class TestEasyExcel {
    public static void main(String[] args) throws Exception {
        // 写法1
        // 1.设置写入文件夹地址和excel文件名称
        String fileName = "F:\\write.xlsx";

        //2.调用easyExcel里面的方法实现写操作
        // write方法两个参数：第一个参数文件路径名称，第二个参数实体类class
        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(data());
    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("张三"+i);
            list.add(data);
        }
        return list;
    }

}
