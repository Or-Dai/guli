package com.lyf.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyf.eduservice.entity.EduSubject;
import com.lyf.eduservice.entity.excel.ExcelSubjectData;
import com.lyf.eduservice.entity.subject.OneSubject;
import com.lyf.eduservice.entity.subject.TwoSubject;
import com.lyf.eduservice.listener.SubjectExcelListener;
import com.lyf.eduservice.mapper.EduSubjectMapper;
import com.lyf.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyf.cmsservice.exception.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-03-07
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file ,EduSubjectService subjectService) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }

    /**
     * 课程分类列表(树形）
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        // 1. 查询所有一级分类  parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");

        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        // 这种方式也可以调用
        //this.list(wrapperOne)

        // 2. 查询所有二级分类  parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");

        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建list集合， 用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();


        // 3. 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值
        //封装到要求的list集合里面ArrayList<OneSubject> finalSubjectList

        for (int i=0 ; i< oneSubjectList.size(); i++){ //遍历oneSubjectList集合
            EduSubject eduSubject = oneSubjectList.get(i);
            // 把eduSubject里面值获取出来，放到OneSubject对象里面

            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.setId());
//            oneSubject.setTitle(eduSubject.setTitle());
            // 多个OneSubject放到finalSubject里面
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //遍历二级分类list集合
            for(int j=0; j< twoSubjectList.size();j++){
                //获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(j);
                //判断二级分类parentId和以及分类Id是否一样
                if(tSubject.getParentId().equals(eduSubject.getId())){
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //放一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }
}
