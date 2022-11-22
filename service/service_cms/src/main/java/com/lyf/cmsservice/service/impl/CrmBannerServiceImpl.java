package com.lyf.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.cmsservice.entity.CrmBanner;
import com.lyf.cmsservice.mapper.CrmBannerMapper;
import com.lyf.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-07-17
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public void pageBanner(Page<CrmBanner> pageParam, Object o) {

    }

    @Override
    public CrmBanner getBannerById(String id) {
        return null;
    }

    @Override
    public void saveBanner(CrmBanner banner) {

    }

    @Override
    public void updateBannerById(CrmBanner banner) {

    }

    @Override
    public void removeBannerById(String id) {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        baseMapper.delete(wrapper);

    }

    /**
     * 查询所有banner
     * @return
     */
    @Cacheable(value = "banner",key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectIndexList() {

        //根据id进行降序排序，显示排列之后前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法,拼接sql语句
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(wrapper);
        return list;
    }
}
