package com.fh.shop.exception;

import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ServerResponse headleGlobalException(GlobalException globalException) {
        ResponseEnum responseEnum1 = globalException.getResponseEnum();
        return ServerResponse.error(responseEnum1);
    }
}
