package com.online.edu.videoservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.online.edu.common.R;
import com.online.edu.videoservice.service.VideoService;
import com.online.edu.videoservice.utils.AliYunSDKUtils;
import com.online.edu.videoservice.utils.ConstantPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

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

    //根据video_source_id获取播放凭证
    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid) throws ClientException {
        DefaultAcsClient client = AliYunSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(vid);
        response = client.getAcsResponse(request);
        //播放凭证
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
        //VideoMeta信息
        System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
        String playAuth = response.getPlayAuth();
        return R.ok().data("playAuth",playAuth);
    }

    @GetMapping("getVideoInfo")
    public R getVideoInfo(@RequestParam("listId") List<String> listId) throws ClientException {
        Map<String,Object> map = videoService.getInfo(listId);
        return R.ok().data("map",map);
    }

    @GetMapping("getMezzanine/{vid}")
    public R getMezzanine(@PathVariable String vid) throws ClientException {
        String url = videoService.getMezz(vid);
        return R.ok().data("url",url);
    }
//根据url对视频进行下载
    @GetMapping("httpDownload/{httpUrl}/{saveFile}")
    public R httpDownload(@PathVariable("httpUrl") String httpUrl,
                                @PathVariable("saveFile") String saveFile) {
        boolean result = false;
        // 1.下载网络文件
        int byteRead;
        URL url = null;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            result =  false;
        }

        try {
            //2.获取链接
            URLConnection conn = url.openConnection();
            //3.输入流
            InputStream inStream = conn.getInputStream();
            //3.写入文件
            FileOutputStream fs = new FileOutputStream(saveFile);

            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            inStream.close();
            fs.close();
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = false;
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        if(result) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}
