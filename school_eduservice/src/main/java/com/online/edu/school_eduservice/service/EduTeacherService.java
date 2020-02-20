package com.online.edu.school_eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.school_eduservice.entity.EduCourse;
import com.online.edu.school_eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.school_eduservice.entity.query.queryTeacher;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ty
 * @since 2020-01-28
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //条件查询带分页
    void pageListCondition(Page<EduTeacher> teacherPage, queryTeacher queryteacher);

    Map<String, Object> getPageTeacherList(Page<EduTeacher> pageTeacher);

    List<EduCourse> getTeacherCourse(String id);
}
