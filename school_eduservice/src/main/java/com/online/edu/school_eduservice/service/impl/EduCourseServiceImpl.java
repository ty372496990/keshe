package com.online.edu.school_eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.school_eduservice.entity.EduCourse;
import com.online.edu.school_eduservice.entity.EduCourseDescription;
import com.online.edu.school_eduservice.entity.form.courseInfoForm;
import com.online.edu.school_eduservice.entity.query.courseQuery;
import com.online.edu.school_eduservice.entity.vo.CoursePublishVo;
import com.online.edu.school_eduservice.entity.vo.CourseWebVo;
import com.online.edu.school_eduservice.handler.EduException;
import com.online.edu.school_eduservice.mapper.EduCourseMapper;
import com.online.edu.school_eduservice.service.EduChapterService;
import com.online.edu.school_eduservice.service.EduCourseDescriptionService;
import com.online.edu.school_eduservice.service.EduCourseService;
import com.online.edu.school_eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ty
 * @since 2020-02-09
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    EduChapterService eduChapterService;
    @Autowired
    EduVideoService eduVideoService;
    @Override
    public String insertCourseInfo(courseInfoForm infoForm) {
        //课程基本信息到信息表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(infoForm,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        //若失败
        if(insert == 0) {
            throw new EduException(20001,"添加课程信息失败");
        }

        //添加课程描述到描述表
        EduCourseDescription description = new EduCourseDescription();
        String description1 = infoForm.getDescription();
        description.setDescription(description1);
        description.setId(eduCourse.getId());
        boolean save = eduCourseDescriptionService.save(description);
        if(save){
            return eduCourse.getId();
        }else {
            return null;
        }
    }

    @Override
    public courseInfoForm getCourseById(String id) {
        //查询课程基本信息
        EduCourse eduCourse = baseMapper.selectById(id);
        if(eduCourse == null){
            throw new EduException(20001,"没有查询到数据！");
        }
        courseInfoForm infoForm = new courseInfoForm();
        BeanUtils.copyProperties(eduCourse,infoForm);
        //查询课程描述信息
        EduCourseDescription byId = eduCourseDescriptionService.getById(id);
        infoForm.setDescription(byId.getDescription());
        return infoForm;
    }

    @Override
    public Boolean updateCourse(courseInfoForm infoForm) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(infoForm,eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if(result == 0) {
            throw new EduException(20001,"修改信息失败！");
        }

        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(infoForm,description);
        boolean b = eduCourseDescriptionService.updateById(description);
        return b;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, courseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public Boolean delteCourse(String id) {
        //根据课程id删除章节
        eduChapterService.deleteChapterByCourseId(id);
        //根据课程id删除小节
        eduVideoService.deleteVideoByCourseId(id);
        //根据课程id删除描述
        eduCourseDescriptionService.removeById(id);
        //删除课程本身
        int i = baseMapper.deleteById(id);
        return i > 0;
    }

    @Override
    public CoursePublishVo getCourseAllInfo(String courseId) {
        CoursePublishVo courseInfoAll = baseMapper.getCourseInfoAll(courseId);
        return courseInfoAll;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }


    @Override
    public CourseWebVo selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
