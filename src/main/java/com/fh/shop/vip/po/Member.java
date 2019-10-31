package com.fh.shop.vip.po;

import java.io.Serializable;

public class Member implements Serializable {
    private int code; //短信状态码
    private String msg; //信息
    private String obj;  //数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
