package com.online.edu.videoservice.controller;

import com.aliyuncs.exceptions.ClientException;
import com.online.edu.common.R;
import com.online.edu.videoservice.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/school_videoservice/video")
@CrossOrigin
public class VideoController {
    @Autowired
    VideoService videoService;
    //实现批量的阿里云视频删除
    @DeleteMapping("removeMoreVideo")
    public R removeMore(@RequestParam("listId") List listId) throws ClientException {
        videoService.deleteMoreVideo(listId);
        return R.ok();
    }
    //实现上传视频到阿里云服务器
    @PostMapping("upload")
    public R uploadVideo(@RequestParam("file") MultipartFile file) {
        //调用方法实现上传
        String videoID = videoService.uploadVideoAliYun(file);
        return R.ok().data("videoID", videoID);
    }

    //实现对阿里云视频的删除
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId) throws ClientException {
        videoService.deleteVideoAliYun(videoId);
        return R.ok();
    }
}
