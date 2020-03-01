package com.online.edu.school_eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.school_eduservice.entity.EduVideo;
import com.online.edu.school_eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author ty
 * @since 2020-02-12
 */
@RestController
@RequestMapping("/school_eduservice/edu-video")
public class EduVideoController {
    @Autowired
    EduVideoService eduVideoService;
    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        boolean save = eduVideoService.save(eduVideo);
        if(save) {
            return R.ok();
        }else {
            return R.error();
        }
    }
    //查询小节
    @GetMapping("{videoId}")
    public R getVideo(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo",eduVideo);
    }

    //修改方法
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        boolean b = eduVideoService.updateById(eduVideo);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //删除小节
    @DeleteMapping("deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        Boolean b = eduVideoService.removeVideoById(videoId);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
    }
    //查询所有小节信息，并获取视频源id
    @GetMapping("getAllVideoId")
    public R getAllVideoId() {
        List<EduVideo> list = eduVideoService.list();
        List<String> listId = new ArrayList<>();
        for (EduVideo eduVideo : list) {
            if(!StringUtils.isEmpty(eduVideo.getVideoSourceId()))
            listId.add(eduVideo.getVideoSourceId());
        }
        return R.ok().data("listId",listId);
    }
}

