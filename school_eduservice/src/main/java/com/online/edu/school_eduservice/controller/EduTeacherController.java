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
@CrossOrigin
public class EduTeacherController {

    //{"code":20000,"data":{"token":"admin-token"}}
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin-token");
    }
    //{"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("introduction","I am a super administrator").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").data("name","Super Admin");
    }
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
    public R deleteById(@PathVariable String id){
        boolean b = eduTeacherService.removeById(id);
        if(b) {
            return R.ok();
        }else {
            return R.error();
        }
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
    public R getMoreConditionPageList(@PathVariable Long page, @PathVariable Long limit,
                                       @RequestBody(required = false) queryTeacher queryteacher)
    {
        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        eduTeacherService.pageListCondition(teacherPage,queryteacher);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return R.ok().data("total",total).data("items",records);
    }

    //添加教师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据ID查询讲师
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("teachers",byId);
    }

    //根据ID修改
    @PostMapping("upDateTeacher/{id}")
    public R upDateTeacher(@PathVariable String id,
                            @RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        }else{
            return R.error();
        }
    }
}

