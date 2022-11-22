package com.lyf.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyf.cmsservice.R;
import com.lyf.cmsservice.entity.CrmBanner;
import com.lyf.cmsservice.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台banner管理
 * </p>
 *
 * @author lyf
 * @since 2022-07-17
 */
@RestController
@RequestMapping("/educms/crm-banner")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    /**
     *  分页查询
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageBanner/{page}/{limit}")
    public R index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<CrmBanner> pageParam = new Page<>(page, limit);
        bannerService.pageBanner(pageParam,null);
        return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    /**
     * 根据ID获取
     * @param id
     * @return
     */
    @ApiOperation(value = "获取Banner")
    @GetMapping("/get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getBannerById(id);
        return R.ok().data("item", banner);
    }

    /**
     * 新增
     * @param banner
     * @return
     */
    @ApiOperation(value = "新增Banner")
    @PostMapping("/add")
    public R save(@RequestBody CrmBanner banner) {
        bannerService.saveBanner(banner);
        return R.ok();
    }

    /**
     * 修改
     * @param banner
     * @return
     */
    @ApiOperation(value = "修改Banner")
    @PutMapping("/update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateBannerById(banner);
        return R.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeBannerById(id);
        return R.ok();
    }
}

