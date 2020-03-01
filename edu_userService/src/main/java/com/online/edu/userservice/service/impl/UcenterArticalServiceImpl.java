package com.online.edu.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.userservice.entity.UcenterArtical;
import com.online.edu.userservice.mapper.UcenterArticalMapper;
import com.online.edu.userservice.service.UcenterArticalService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ty
 * @since 2020-02-24
 */
@Service
public class UcenterArticalServiceImpl extends ServiceImpl<UcenterArticalMapper, UcenterArtical> implements UcenterArticalService {

    @Override
    public Map<String, Object> pageListArt(Page<UcenterArtical> page1) {
        QueryWrapper<UcenterArtical> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");

        baseMapper.selectPage(page1, queryWrapper);

        List<UcenterArtical> records = page1.getRecords();
        long current = page1.getCurrent();
        long pages = page1.getPages();
        long size = page1.getSize();
        long total = page1.getTotal();
        boolean hasNext = page1.hasNext();
        boolean hasPrevious = page1.hasPrevious();

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
}
