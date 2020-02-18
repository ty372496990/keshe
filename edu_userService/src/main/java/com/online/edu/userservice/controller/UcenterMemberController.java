package com.online.edu.userservice.controller;


import com.online.edu.common.R;
import com.online.edu.userservice.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ty
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/userservice/ucenter-member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;


    @GetMapping("sumRegister/{day}")
    public R sumRegister(@PathVariable("day") String day){
        Integer result = ucenterMemberService.registerCount(day);
        return R.ok().data("result",result);
    }
}
