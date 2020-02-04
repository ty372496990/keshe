package com.online.edu.school_eduservice.handler;

import com.online.edu.common.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class globalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R popError(Exception e){
        e.printStackTrace();
        return R.error().message("出现了异常！");
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public R popError(EduException e){
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
