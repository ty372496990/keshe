package com.online.edu.userservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.userservice.entity.UcenterArtical;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ty
 * @since 2020-02-24
 */
public interface UcenterArticalService extends IService<UcenterArtical> {

    Map<String, Object> pageListArt(Page<UcenterArtical> page1);
}
