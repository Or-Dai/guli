package com.lyf.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyf.eduservice.entity.EduChapter;
import com.lyf.eduservice.entity.EduVideo;
import com.lyf.eduservice.entity.chapter.ChapterVo;
import com.lyf.eduservice.entity.chapter.VideoVo;
import com.lyf.eduservice.mapper.EduChapterMapper;
import com.lyf.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyf.eduservice.service.EduVideoService;
import com.lyf.cmsservice.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-03-12
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    @Autowired
    private EduVideoService videoService;

    /**
     * 课程大纲列表，根据课程id进行查询
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {

        //1. 根据课程Id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);


        //2.根据课程Id查询课程里面所有的小结
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        // 创建list集合，用于最终封装数据
        ArrayList<ChapterVo> finalList = new ArrayList<>();

        //3.遍历查询章节List集合进行封装
          //遍历查询章节list集合
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            finalList.add(chapterVo);

            //创建集合，用于封装章节的小节
            List<VideoVo> videoList = new ArrayList<>();
            //4.遍历查询小节集合，进行封装
            for (int j = 0; j < eduVideoList.size(); j++) {
                    //得到每个小节
                    EduVideo eduVideo = eduVideoList.get(j);
                    //判断：小节里面chapterid和章节里面id是否一样
                    if(eduVideo.getChapterId().equals(eduChapter.getId())){
                        //进行封装
                        VideoVo videoVo = new VideoVo();
                        BeanUtils.copyProperties(eduVideo,videoVo);
                        //放到小节封装集合
                        videoList.add(videoVo);
                    }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    /**
     * 删除章节的方法
     * @param chapterId
     * @return
     */
    @Override
    public boolean deleteChapter(String chapterId) {

        //根据chapterid章节小节，查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if (count >0){
            //查询出小节，不进行删除，抛出异常
            throw  new GuliException(20001,"不能删除");
        }else{
            //不能查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);

            //成功 1>0  0>0;
            return result>0;
        }
    }

    /**
     * 根据Id删除章节
     * @param courseId
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
