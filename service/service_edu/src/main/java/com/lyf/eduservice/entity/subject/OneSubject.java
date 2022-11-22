package com.lyf.eduservice.entity.subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description ： 一级分类
 * @Date: Create in 2022/3/10 22:30
 * @Modified By:
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneSubject {
    private String id;
    private String title;
    //一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
