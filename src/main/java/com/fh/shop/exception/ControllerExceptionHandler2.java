package com.fh.shop.exception;

import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler2 {

    @ExceptionHandler(GlobalException2.class)
    public ServerResponse aa(GlobalException2 globalException2) {
        ResponseEnum responseEnum = globalException2.getResponseEnum();
        return ServerResponse.error(responseEnum);
    }
}
