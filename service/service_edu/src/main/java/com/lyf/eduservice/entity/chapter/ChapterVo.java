package com.lyf.eduservice.entity.chapter;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/14 14:42
 * @Modified By:
 */
@ApiModel(value = "章节信息")
@Data
public class ChapterVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    //表示小节
    private List<VideoVo> children = new ArrayList<>();
}
