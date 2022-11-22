package com.lyf.ucenterservice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyf.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author lyf
 * @since 2022-07-18
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    UcenterMember selectOne(QueryWrapper<Object> wrapper);

    /**
     *  统计人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);
}
