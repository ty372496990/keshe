package com.online.edu.videoservice.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    String uploadVideoAliYun(MultipartFile file);

    void deleteVideoAliYun(String videoId) throws ClientException;
}
