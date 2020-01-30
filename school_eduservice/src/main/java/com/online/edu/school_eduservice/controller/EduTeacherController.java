package com.online.edu.school_eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.school_eduservice.entity.EduTeacher;
import com.online.edu.school_eduservice.entity.query.queryTeacher;
import com.online.edu.school_eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ty
 * @since 2020-01-28
 */
@RestController
@RequestMapping("/school_eduservice/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;
    //查询全部教师数据
    @GetMapping
    public R getAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }
    //逻辑删除
    @DeleteMapping("{id}")
    public boolean deleteById(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        return b;
    }

    //分页
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long page,@PathVariable Long limit)
    {
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        eduTeacherService.page(teacherPage,null);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total",total).data("items",records);
    }
    //多条件组合分页查询
    @PostMapping("moreConditionPageList/{page}/{limit}")
    public  R getMoreConditionPageList(@PathVariable Long page, @PathVariable Long limit,
                                       @RequestBody(required = false) queryTeacher queryteacher)
    {
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        eduTeacherService.pageListCondition(teacherPage,queryteacher);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total",total).data("items",records);
    }
}

