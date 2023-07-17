package com.sa.controller;

import com.sa.controller.utils.R;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//异常处理器
@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public R doException(Exception exception){
        System.out.println(exception);
        // 记录日志
        return R.mistake("服务器故障！");
    }
}
