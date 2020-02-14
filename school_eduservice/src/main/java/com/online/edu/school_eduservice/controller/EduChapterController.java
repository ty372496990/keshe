package com.online.edu.school_eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.school_eduservice.entity.EduChapter;
import com.online.edu.school_eduservice.entity.vo.ChapterVo;
import com.online.edu.school_eduservice.service.EduChapterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ty
 * @since 2020-02-12
 */
@RestController
@RequestMapping("/school_eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "嵌套章节数据列表")
    @GetMapping("getChapterList/{courseId}")
    public R getListByCourseId(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        List<ChapterVo> chapterVoList = chapterService.getChapterList(courseId);
        return R.ok().data("items", chapterVoList);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        boolean save = chapterService.save(eduChapter);
        if(save) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据id查找章节
    @GetMapping("getChapter/{id}")
    public R getChapter(@PathVariable String id){
        EduChapter eduChapter = chapterService.getById(id);
        return R.ok().data("eduChapter",eduChapter);
    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        boolean b = chapterService.updateById(eduChapter);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据ID删除章节")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "章节ID", required = true)
            @PathVariable String id){

        boolean result = chapterService.removeChapterById(id);
        if(result){
            return R.ok();
        }else{
            return R.error().message("删除失败");
        }
    }
}

