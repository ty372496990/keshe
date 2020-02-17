package com.online.edu.videoservice.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VideoService {
    String uploadVideoAliYun(MultipartFile file);

    void deleteVideoAliYun(String videoId) throws ClientException;

    void deleteMoreVideo(List<String> listId) throws ClientException;
}
