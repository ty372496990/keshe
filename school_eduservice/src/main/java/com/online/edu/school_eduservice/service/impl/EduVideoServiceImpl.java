package com.online.edu.school_eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.school_eduservice.client.videoClient;
import com.online.edu.school_eduservice.entity.EduVideo;
import com.online.edu.school_eduservice.mapper.EduVideoMapper;
import com.online.edu.school_eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author ty
 * @since 2020-02-12
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    videoClient videoClient;
    @Override
    public void deleteVideoByCourseId(String id) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }

    @Override
    public boolean getCountByChapterId(String id) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public Boolean removeVideoById(String videoId) {
        EduVideo eduVideo = baseMapper.selectById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoId)) {
            videoClient.deleteVideoAliYun(videoSourceId);
        }
        int i = baseMapper.deleteById(videoId);
        return i > 0;
    }
}
