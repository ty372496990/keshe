package com.online.edu.school_eduservice.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("school-videoService")
@Component
public interface videoClient {
    @DeleteMapping("/school_videoservice/video/{videoId}")
    public R deleteVideoAliYun(@PathVariable("videoId") String videoId);
}
