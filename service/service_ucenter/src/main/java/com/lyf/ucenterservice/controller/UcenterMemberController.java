package com.lyf.ucenterservice.controller;


import com.lyf.cmsservice.JwtUtils;
import com.lyf.cmsservice.R;
import com.lyf.cmsservice.ordervo.UcenterMemberOrder;
import com.lyf.ucenterservice.entity.UcenterMember;
import com.lyf.ucenterservice.entity.vo.RegisterVo;
import com.lyf.ucenterservice.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lyf
 * @since 2022-07-18
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    /**
     *  登录接口
     * @param member
     * @return
     */
    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public R login(@RequestBody UcenterMember member) {
        // 调用service方法实现登录
        // 返回token， 使用jwt登录
        String token = memberService.login(member);
        return R.ok().data("token", token);
    }


    /**
     *  注册接口
     * @param registerVo
     * @return
     */
    @PostMapping("/register")
    public R register (@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }


    /**
     *根据token获取登录信息
     * @param request
     * @return
     */
    @GetMapping("getMemberInfo")
    public R getMemberInfo (HttpServletRequest request){
        // 调用jwt 工具类的方法。根据request对象获取头信息，返回用户Id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库根据用户Id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }


    //根据用户id获取客户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember ucenterMember = memberService.getById(id);
        //把UcenterMember复制为UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }


    /**
     * 查询某一天注册人数
     * @param day
     * @return
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        Integer count = memberService.countRegisterDay(day);
        return  R.ok().data("countRegister",count);
    }

}

