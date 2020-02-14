package com.online.edu.school_eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.school_eduservice.entity.EduChapter;
import com.online.edu.school_eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author ty
 * @since 2020-02-12
 */
public interface EduChapterService extends IService<EduChapter> {
    void deleteChapterByCourseId(String id);

    List<ChapterVo> getChapterList(String courseId);

    boolean removeChapterById(String id);
}
