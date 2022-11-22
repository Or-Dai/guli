package com.lyf.eduservice.controller;

import com.lyf.cmsservice.R;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: LiangYiFeng
 * @Description
 * @Date: Create in 2022/3/3 20:27
 * @Modified By:
 */
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin //解决跨域
public class EduLoginController {


    @PostMapping("/login")
    public R login(){

        return R.ok().data("token","admin");
    }


    @GetMapping("/info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://img0.baidu.com/it/u=857510153,4267238650&fm=253&fmt=auto&app=120&f=JPEG?w=80&h=80");
    }
}
