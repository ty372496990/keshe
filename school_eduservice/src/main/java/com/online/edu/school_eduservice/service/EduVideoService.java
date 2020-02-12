package com.online.edu.school_eduservice.service;

import com.online.edu.school_eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author ty
 * @since 2020-02-12
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideoByCourseId(String id);
}
