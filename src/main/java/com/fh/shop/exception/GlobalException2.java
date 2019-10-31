package com.fh.shop.exception;

import com.fh.shop.common.ResponseEnum;

public class GlobalException2 extends RuntimeException {
    private ResponseEnum responseEnum;

    public GlobalException2(ResponseEnum responseEnum) {
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return this.responseEnum;
    }

}
