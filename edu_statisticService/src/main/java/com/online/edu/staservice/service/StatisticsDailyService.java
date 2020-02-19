package com.online.edu.staservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.staservice.entity.StatisticsDaily;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author ty
 * @since 2020-02-18
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void countUserNum(String day);

    Map<String, Object> getCount(String type, String begin, String end);
}
