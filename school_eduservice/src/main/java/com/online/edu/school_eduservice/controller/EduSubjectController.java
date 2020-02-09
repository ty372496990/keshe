package com.online.edu.school_eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.school_eduservice.entity.EduSubject;
import com.online.edu.school_eduservice.entity.vo.SubjectNestedVo;
import com.online.edu.school_eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService eduSubjectService;
    //上传文件
    @PostMapping("import")
    public R importExcelSubject(@RequestParam("file") MultipartFile file){
        //获取上传文件
        List<String> strings = eduSubjectService.importSubject(file);
        if(strings.size() == 0){
            return R.ok();
        }else {
            return R.error().message("导入数据失败！").data("msgList",strings);
        }
    }
    //多级列表查询
    @GetMapping
    public R getSubject(){
//        List<EduSubject> eduSubjects = eduSubjectService.list(null);
        List<SubjectNestedVo> subjectNestedVoList = eduSubjectService.nestlist();
        return R.ok().data("items",subjectNestedVoList);
    }
    //一级列表删除
    @DeleteMapping("{id}")
    public R deleteById(@PathVariable String id){
        boolean b = eduSubjectService.removeSubjectById(id);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //添加一级分类
    @PostMapping("addFirst")
    public R addFirst(@RequestBody EduSubject eduSubject){
        boolean b = eduSubjectService.addFirstLevel(eduSubject);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("addSecond")
    public R addSecond(@RequestBody EduSubject eduSubject){
        boolean b = eduSubjectService.addSecondLevel(eduSubject);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
    }
}

