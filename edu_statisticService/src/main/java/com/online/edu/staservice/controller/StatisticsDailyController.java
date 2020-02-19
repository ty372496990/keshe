package com.online.edu.staservice.controller;


import com.online.edu.common.R;
import com.online.edu.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author ty
 * @since 2020-02-18
 */
@RestController
@RequestMapping("/staservice/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService dailyService;
    //统计用户人数
    @GetMapping("getStatistic/{day}")
    public R getStatistic(@PathVariable("day") String day){
        dailyService.countUserNum(day);
        return R.ok();
    }

    //返回显示图表的数据
    @GetMapping("getDataCount/{type}/{begin}/{end}")
    public R getDataCount(@PathVariable String type,
                          @PathVariable String begin,
                          @PathVariable String end) {
        Map<String,Object> map= dailyService.getCount(type,begin,end);
        return R.ok().data(map);
    }
}

