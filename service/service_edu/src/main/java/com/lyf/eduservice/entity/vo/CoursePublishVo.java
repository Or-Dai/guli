package com.lyf.eduservice.entity.vo;

import lombok.Data;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/16 17:11
 * @Modified By:
 */
@Data
public class CoursePublishVo {

    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示

}
