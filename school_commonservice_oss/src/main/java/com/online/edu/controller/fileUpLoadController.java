package com.online.edu.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.online.edu.common.R;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/school_eduservice/oss")
@CrossOrigin
public class fileUpLoadController {
    //上传头像
    @PostMapping("upload")
    public R upLoadFile(@RequestParam("file") MultipartFile file) throws IOException {
        //获取上传文件的名称，获取文件的输入流
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4Frw9bvsv3fKdxHyoMD1";
        String accessKeySecret = "FzYqzJ8pGJS8VYF1F0dl7WbBvoaqGe";
        String bucketName = "ty-edu1";

        String originalFilename = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        //上传至oss
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        ossClient.putObject(bucketName, originalFilename, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        String path = "http://" + bucketName + "." + endpoint + "/" + originalFilename;
        return R.ok().data("imgurl", path);
    }
}
