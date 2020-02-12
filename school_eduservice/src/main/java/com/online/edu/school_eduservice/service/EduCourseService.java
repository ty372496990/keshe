package com.online.edu.school_eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.school_eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.school_eduservice.entity.form.courseInfoForm;
import com.online.edu.school_eduservice.entity.query.courseQuery;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ty
 * @since 2020-02-09
 */
public interface EduCourseService extends IService<EduCourse> {

    String insertCourseInfo(courseInfoForm infoForm);

    courseInfoForm getCourseById(String id);

    Boolean updateCourse(courseInfoForm infoForm);

    void pageQuery(Page<EduCourse> pageParam, courseQuery courseQuery);

    Boolean delteCourse(String id);
}
