package com.online.edu.school_eduservice.mapper;

import com.online.edu.school_eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.school_eduservice.entity.vo.CoursePublishVo;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2020-02-09
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询详细信息
    CoursePublishVo getCourseInfoAll(@RequestParam("id") String courseId);
}
