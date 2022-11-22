package com.lyf.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.cmsservice.R;
import com.lyf.cmsservice.entity.CrmBanner;
import com.lyf.cmsservice.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-07-17
 */
@RestController
@RequestMapping("/educms/banner")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     * 查询所有banner
     * @return
     */
    @ApiOperation(value = "获取首页banner")
    @GetMapping("/getAllBanner")
    public R index() {
        List<CrmBanner> list = bannerService.selectIndexList();
        return R.ok().data("list", list);
    }
}

