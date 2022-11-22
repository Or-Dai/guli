package com.lyf.eduservice.service;

import com.lyf.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyf.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节的方法
    boolean deleteChapter(String chapterId);
    //根据Id删除章节
    void removeChapterByCourseId(String courseId);
}
