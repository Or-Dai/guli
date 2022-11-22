package com.lyf.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/14 14:43
 * @Modified By:
 */
@ApiModel(value = "小节信息")
@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private Boolean free;

    private String videoSourceId; //视频ID
}
