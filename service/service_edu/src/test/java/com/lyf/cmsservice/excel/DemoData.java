package com.lyf.cmsservice.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/7 11:20
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemoData {

    //设置表头名称
    @ExcelProperty(value = "学生编号",index = 0)
    private int sno;

    //设置表头名称
    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;


}
