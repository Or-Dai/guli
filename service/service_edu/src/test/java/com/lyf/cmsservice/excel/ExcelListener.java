package com.lyf.cmsservice.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/7 11:34
 * @Modified By:
 */
//创建读取excel监听器
public class ExcelListener extends AnalysisEventListener<DemoData> {

    //创建list集合封装最终的数据
    List<DemoData> list = new ArrayList<DemoData>();

    //一行一行去读取excle内容
    @Override
    public void invoke(DemoData data, AnalysisContext analysisContext) {
        System.out.println("***"+data);
        list.add(data);
    }

    //读取excel表头信息
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头信息："+headMap);
    }

    //读取完成后执行
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
