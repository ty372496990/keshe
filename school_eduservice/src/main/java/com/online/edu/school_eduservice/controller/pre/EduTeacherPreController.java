package com.online.edu.school_eduservice.controller.pre;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.school_eduservice.entity.EduCourse;
import com.online.edu.school_eduservice.entity.EduTeacher;
import com.online.edu.school_eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/school_eduservice/teacher")
@CrossOrigin
public class EduTeacherPreController {
    @Autowired
    EduTeacherService eduTeacherService;
    //查找讲师分页信息
    @GetMapping("getPreTeacherListPage/{page}/{limit}")
    public R getPreTeacherListPage(@PathVariable long page,
                                   @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = eduTeacherService.getPageTeacherList(pageTeacher);
        return R.ok().data(map);
    }

    //查询讲师描述和课程信息
    @GetMapping("getTeacherIntro/{id}")
    public R getTeacherIntro(@PathVariable String id) {
        //获取教师描述
        EduTeacher teacher = eduTeacherService.getById(id);

        //获取教师所讲课程信息
        List<EduCourse> list = eduTeacherService.getTeacherCourse(id);
        return R.ok().data("teacher",teacher).data("list",list);
    }
}
