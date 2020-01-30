package com.online.edu.school_eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.school_eduservice.entity.EduTeacher;
import com.online.edu.school_eduservice.entity.query.queryTeacher;
import com.online.edu.school_eduservice.mapper.EduTeacherMapper;
import com.online.edu.school_eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ty
 * @since 2020-01-28
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    //条件查询带分页
    @Override
    public void pageListCondition(Page<EduTeacher> teacherPage, queryTeacher queryteacher) {
        if(queryteacher == null){
            //如果没有条件则查询全部
            baseMapper.selectPage(teacherPage,null);
            return;
        }
        //取出条件值
        String name = queryteacher.getName();
        String end = queryteacher.getEnd();
        String level = queryteacher.getLevel();
        String begin = queryteacher.getBegin();

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create", begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create", end);
        }


        baseMapper.selectPage(teacherPage,wrapper);
    }
}
