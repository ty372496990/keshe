package com.online.edu.videoservice.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.online.edu.videoservice.service.VideoService;
import com.online.edu.videoservice.utils.AliYunSDKUtils;
import com.online.edu.videoservice.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VideoServiceImpl implements VideoService {
    @Override
    public String uploadVideoAliYun(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET
                    , title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            }
            return response.getVideoId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //删除阿里云的视频
    @Override
    public void deleteVideoAliYun(String videoId) throws ClientException {
        DefaultAcsClient client = AliYunSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(videoId);
        DeleteVideoResponse response = client.getAcsResponse(request);
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
    //批量删除阿里云的视频
    @Override
    public void deleteMoreVideo(List<String> listId) throws ClientException{
        DefaultAcsClient client = AliYunSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        String join = StringUtils.join(listId.toArray(), ',');
        request.setVideoIds(join);
        DeleteVideoResponse response = client.getAcsResponse(request);
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    @Override
    public Map<String, Object> getInfo(List<String> listId) throws ClientException {
        DefaultAcsClient client = AliYunSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        GetVideoInfosRequest request = new GetVideoInfosRequest();
        String join = StringUtils.join(listId.toArray(), ',');
        request.setVideoIds(join);
        GetVideoInfosResponse response = client.getAcsResponse(request);

        //创建HashMap来保存数据
        Map<String,Object> map = new HashMap<>();
        if (response.getVideoList() != null && response.getVideoList().size() > 0) {
            System.out.print("===== exist video infos : ===== \n");
            for (GetVideoInfosResponse.Video video : response.getVideoList()) {
                System.out.print("VideoId = " + video.getVideoId() + "\n");
                System.out.print("Title = " + video.getTitle() + "\n");
                System.out.print("Description = " + video.getDescription() + "\n");
                System.out.print("Tags = " + video.getTags() + "\n");
                System.out.print("CreationTime = " + video.getCreationTime() + "\n");
                map.put("title",video.getTitle());
            }
        }
        return map;
    }
    //根据视频源id获取视频url
    @Override
    public String getMezz(String vid) throws ClientException {
        DefaultAcsClient client = AliYunSDKUtils.initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID, ConstantPropertiesUtil.ACCESS_KEY_SECRET);
        GetMezzanineInfoResponse response = new GetMezzanineInfoResponse();
        GetMezzanineInfoRequest request = new GetMezzanineInfoRequest();
        request.setVideoId(vid);
        //源片下载地址过期时间
        request.setAuthTimeout(3600L);

        response = client.getAcsResponse(request);

        System.out.print("Mezzanine.VideoId = " + response.getMezzanine().getVideoId() + "\n");
        System.out.print("Mezzanine.FileURL = " + response.getMezzanine().getFileURL() + "\n");
        System.out.print("Mezzanine.Width = " + response.getMezzanine().getWidth() + "\n");
        System.out.print("Mezzanine.Height = " + response.getMezzanine().getHeight() + "\n");
        System.out.print("Mezzanine.Size = " + response.getMezzanine().getSize() + "\n");
        return response.getMezzanine().getFileURL();
    }
}
