package com.online.edu.userservice.controller;


import com.online.edu.common.R;
import com.online.edu.userservice.entity.UcenterMember;
import com.online.edu.userservice.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;


    @GetMapping("sumRegister/{day}")
    public R sumRegister(@PathVariable("day") String day){
        Integer result = ucenterMemberService.registerCount(day);
        return R.ok().data("result",result);
    }
    //注册用户
    @PostMapping("addUser")
    public R addUser(@RequestBody UcenterMember member) {
        boolean save = ucenterMemberService.save(member);
        if(save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //查找用户
    @GetMapping("/getUserById/{id}")
    public R getUserById(@PathVariable String id) {
        UcenterMember member = ucenterMemberService.getById(id);
        return R.ok().data("member",member);
    }

    //通过查找用户信息获取用户id
    @GetMapping("getUserId/{mobile}/{nickName}")
    public R getUserId(@PathVariable String mobile,
                       @PathVariable String nickName) {
        String id = ucenterMemberService.getUserId(mobile,nickName);
        return R.ok().data("id",id);
    }
}
