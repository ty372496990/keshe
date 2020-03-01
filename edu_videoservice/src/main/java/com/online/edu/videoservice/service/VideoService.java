package com.online.edu.videoservice.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    String uploadVideoAliYun(MultipartFile file);

    void deleteVideoAliYun(String videoId) throws ClientException;

    void deleteMoreVideo(List<String> listId) throws ClientException;

    Map<String, Object> getInfo(List<String> listId) throws ClientException;

    String getMezz(String vid) throws ClientException;
}
