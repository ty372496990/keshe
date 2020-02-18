package com.online.edu.staservice.client;

import com.online.edu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("school-useService")
public interface userClient {
    @GetMapping("/userservice/ucenter-member/sumRegister/{day}")
    public R countUserNum(@PathVariable("day") String day);
}
