package com.online.edu.userservice.service;

import com.online.edu.userservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author ty
 * @since 2020-02-18
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    Integer registerCount(String day);

    String getUserId(String mobile, String nickName);

    UcenterMember isLoginById(String nickName, String password);
}
