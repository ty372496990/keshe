package com.online.edu.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.common.R;
import com.online.edu.staservice.client.userClient;
import com.online.edu.staservice.entity.StatisticsDaily;
import com.online.edu.staservice.mapper.StatisticsDailyMapper;
import com.online.edu.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author ty
 * @since 2020-02-18
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private userClient userClient;
    //统一日用户数量
    @Override
    public void countUserNum(String day) {
        //如果存在有相同的时间统计，则删除后在进行添加
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);


        R r = userClient.countUserNum(day);
        Integer result = (Integer) r.getData().get("result");
//        System.out.println(result);
        Integer loginNum = RandomUtils.nextInt(100, 200);//TODO
        Integer videoViewNum = RandomUtils.nextInt(100, 200);//TODO
        Integer courseNum = RandomUtils.nextInt(100, 200);//TODO

        //创建统计对象
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(result);
        daily.setLoginNum(loginNum);
        daily.setVideoViewNum(videoViewNum);
        daily.setCourseNum(courseNum);
        daily.setDateCalculated(day);

        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getCount(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        //查询大于开始时间，小于结束时间的数据
        wrapper.between("date_calculated", begin, end);
        //查询时间与种类
        wrapper.select("date_calculated", type);
        Map<String, Object> map = new HashMap<>();

        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();


        for (StatisticsDaily statisticsDaily : statisticsDailies) {
            String dateCalculated = statisticsDaily.getDateCalculated();
            dateList.add(dateCalculated);

            switch (type) {
                case "register_num":
                    numList.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    numList.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    numList.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }

        }
        map.put("dataList", dateList);
        map.put("numList", numList);
        return map;
    }
}
