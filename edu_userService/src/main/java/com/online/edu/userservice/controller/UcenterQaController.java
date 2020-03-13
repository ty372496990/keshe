package com.online.edu.userservice.controller;


import com.online.edu.common.R;
import com.online.edu.userservice.entity.UcenterQa;
import com.online.edu.userservice.service.UcenterQaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ty
 * @since 2020-03-13
 */
@RestController
@RequestMapping("/userservice/ucenter-qa")
@CrossOrigin
public class UcenterQaController {
    @Autowired
    UcenterQaService ucenterQaService;
    @PostMapping("addQa/{name}/{context}")
    public R addQa(@PathVariable String name,
                   @PathVariable String context) {
        UcenterQa ucenterQa = new UcenterQa();
        ucenterQa.setContext(context);
        ucenterQa.setName(name);
        ucenterQaService.save(ucenterQa);
        return R.ok();
    }

    @GetMapping("getAllQa")
    public R getAllQa() {
        List<UcenterQa> list = ucenterQaService.list(null);
        return R.ok().data("QaList",list);
    }
}

