package com.online.edu.school_eduservice.service;

import com.online.edu.school_eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.school_eduservice.entity.vo.SubjectNestedVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author ty
 * @since 2020-02-07
 */
public interface EduSubjectService extends IService<EduSubject> {
//从excel导入分类信息
    List<String> importSubject(MultipartFile file);
//查询全部分类信息
    List<SubjectNestedVo> nestlist();
//删除分类
    boolean removeSubjectById(String id);
//添加一级分类
    boolean addFirstLevel(EduSubject eduSubject);
//添加二级分类
    boolean addSecondLevel(EduSubject eduSubject);
}
