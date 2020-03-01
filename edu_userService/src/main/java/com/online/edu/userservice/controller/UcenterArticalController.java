package com.online.edu.userservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.userservice.entity.UcenterArtical;
import com.online.edu.userservice.service.UcenterArticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ty
 * @since 2020-02-24
 */
@RestController
@RequestMapping("/userservice/ucenter-artical")
@CrossOrigin
public class UcenterArticalController {
    @Autowired
    UcenterArticalService ucenterArticalService;
    //发布文章
    @PostMapping("publishContent")
    public R publishContent(@RequestBody UcenterArtical artical) {
        boolean save = ucenterArticalService.save(artical);
        if(save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页查询文章
    @GetMapping("getUserPage/{page}/{limit}")
    public R getUserPage(@PathVariable long page,
                         @PathVariable long limit){
        Page<UcenterArtical> page1 = new Page<>(page,limit);
        Map<String,Object> map = ucenterArticalService.pageListArt(page1);
        return R.ok().data(map);
    }



}

