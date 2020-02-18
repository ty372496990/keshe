package com.online.edu.userservice.mapper;

import com.online.edu.userservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author ty
 * @since 2020-02-18
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //统计人数
    Integer registerCount(String day);
}
