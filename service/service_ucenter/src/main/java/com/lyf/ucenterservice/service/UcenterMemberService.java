package com.lyf.ucenterservice.service;

import com.lyf.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyf.ucenterservice.entity.vo.RegisterVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lyf
 * @since 2022-07-18
 */
@Transactional(rollbackFor = Exception.class)
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenid(String openid);

    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    Integer countRegisterDay(String day);
}
