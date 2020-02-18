package com.online.edu.userservice.service.impl;

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
}
