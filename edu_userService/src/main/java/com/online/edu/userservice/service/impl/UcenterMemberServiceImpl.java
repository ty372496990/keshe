package com.online.edu.userservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.userservice.entity.UcenterMember;
import com.online.edu.userservice.mapper.UcenterMemberMapper;
import com.online.edu.userservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ty
 * @since 2020-02-18
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public Integer registerCount(String day) {
        return baseMapper.registerCount(day);
    }

    @Override
    public String getUserId(String mobile, String nickName) {
        //通过昵称和电话查询用户的id
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        wrapper.eq("nickname",nickName);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        String id = ucenterMember.getId();
        return id;
    }
}
