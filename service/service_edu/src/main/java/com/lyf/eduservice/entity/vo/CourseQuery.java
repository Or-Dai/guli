package com.lyf.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/20 17:06
 * @Modified By:
 */
@ApiModel(value = "Course查询对象", description = "课程查询对象封装")
@Data
public class CourseQuery {


    @ApiModelProperty(value = "课程名称")
    private String title;


    @ApiModelProperty(value = "课程状态 Normal 已发布 Draft 未发布")
    private Integer level;


    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

}
