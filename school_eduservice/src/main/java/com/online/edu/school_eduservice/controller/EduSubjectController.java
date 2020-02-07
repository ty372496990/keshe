package com.online.edu.school_eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.school_eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author ty
 * @since 2020-02-07
 */
@RestController
@RequestMapping("/school_eduservice/edu-subject")
public class EduSubjectController {
    @Autowired
    EduSubjectService eduSubjectService;
    @PostMapping("import")
    public R importExcelSubject(@RequestParam("file") MultipartFile file){
        //获取上传文件
        List<String> strings = eduSubjectService.importSubject(file);
        return R.ok().data("msgList",strings);
    }
}

