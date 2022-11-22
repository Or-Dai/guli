package com.lyf.eduservice.controller;


import com.lyf.cmsservice.R;
import com.lyf.eduservice.entity.EduChapter;
import com.lyf.eduservice.entity.chapter.ChapterVo;
import com.lyf.eduservice.service.EduChapterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;


    /**
     * 课程大纲列表，根据课程id进行查询
     * @return
     */
    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }


    /**
     * 添加章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }


    /**
     * 修改章节
     * @param chapterId
     * @return
     */
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }


    /**
     * 修改章节
     * @param eduChapter
     * @return
     */
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }


    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("/delete/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }


}

