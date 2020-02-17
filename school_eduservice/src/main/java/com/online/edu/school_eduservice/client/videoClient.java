package com.online.edu.school_eduservice.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("school-videoService")
@Component
public interface videoClient {
    //调用删除单个视频的方法
    @DeleteMapping("/school_videoservice/video/{videoId}")
    public R deleteVideoAliYun(@PathVariable("videoId") String videoId);
    //调用删除多个视频的方法
    @DeleteMapping("/school_videoservice/video/removeMoreVideo")
    public R deleteMoreVideo(@RequestParam("listId")List<String> listId);
}
