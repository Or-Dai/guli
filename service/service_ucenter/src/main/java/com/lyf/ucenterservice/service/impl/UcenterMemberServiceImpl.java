package com.lyf.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyf.cmsservice.JwtUtils;
import com.lyf.cmsservice.MD5;
import com.lyf.cmsservice.exception.GuliException;
import com.lyf.ucenterservice.entity.UcenterMember;
import com.lyf.ucenterservice.entity.vo.RegisterVo;
import com.lyf.ucenterservice.mapper.UcenterMemberMapper;
import com.lyf.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author lyf
 * @since 2022-07-18
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(UcenterMember member) {

        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        // 手机号和密码非空判断
        if(StringUtils.isEmpty(mobile)|| StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登录失败");
        }
        // 判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);

       UcenterMember mobileMember =  baseMapper.selectOne(wrapper);

       //判断查询对象是否为空
        if(mobileMember == null){
            throw new GuliException(20001,"登录失败");
        }

        //判断密码
        //因为存储到数据库的密码是加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式 ： MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"登录失败");
        }

        //判断用户是否禁用
        if(mobileMember.getIsDisabled()){
            throw new GuliException(20001,"登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    /**
     * 注册方法
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        // 验证码
        String code = registerVo.getCode();
        // 手机号
        String mobile = registerVo.getMobile();
        // 昵称
        String nickname = registerVo.getNickname();
        // 密码
        String password = registerVo.getPassword();

        // 非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code) || StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001,"注册失败");
        }

        // 判断验证码
        // 获取redis验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"注册失败");
        }

        // 判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count= baseMapper.selectCount(wrapper);
        if(count > 0){
            throw new GuliException(20001,"注册失败");
        }


        //数据添加到数据库中
        UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        //用户不禁用
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);

    }

    @Override
    public UcenterMember getByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }


}
