package com.online.edu.school_eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.school_eduservice.entity.EduCourse;
import com.online.edu.school_eduservice.entity.form.courseInfoForm;
import com.online.edu.school_eduservice.entity.query.courseQuery;
import com.online.edu.school_eduservice.entity.vo.CoursePublishVo;
import com.online.edu.school_eduservice.service.EduCourseService;
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
 * @since 2020-02-09
 */
@RestController
@RequestMapping("/school_eduservice/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;
    //发布课程，修改课程状态为Normal
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean b = eduCourseService.updateById(eduCourse);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
    }
    //根据课程id得到课程详细信息
    @GetMapping("getAllCourseInfo/{courseId}")
    public R getAllCourseInfo(@PathVariable String courseId){
        CoursePublishVo courseAllInfo=eduCourseService.getCourseAllInfo(courseId);
        return R.ok().data("info",courseAllInfo);
    }
    @PostMapping
    public R addCourseInfo(@RequestBody courseInfoForm infoForm){
        String courseid= eduCourseService.insertCourseInfo(infoForm);
       return R.ok().data("courseid",courseid);
    }
    //获取全部课程信息
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id){
        courseInfoForm form = eduCourseService.getCourseById(id);
        return R.ok().data("form",form);
    }

    //更新课程信息
    @PostMapping("update")
    public R updateCourseInfo(@RequestBody courseInfoForm infoForm){
        Boolean flag = eduCourseService.updateCourse(infoForm);
        if(flag) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    //查询所有课程信息
    @GetMapping("getCourseList")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    courseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(page, limit);

        eduCourseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }

    //删除课程
    @DeleteMapping("deleteCourse/{id}")
    public R deleteCourseById(@PathVariable String id){
        Boolean b = eduCourseService.delteCourse(id);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }

    }
}

