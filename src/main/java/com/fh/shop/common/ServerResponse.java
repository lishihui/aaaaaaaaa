package com.fh.shop.common;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static ServerResponse success(Object data) {
        return new ServerResponse(200, "ok", data);
    }

    public static ServerResponse success() {
        return new ServerResponse(200, "ok", null);
    }

    public static ServerResponse error() {
        return new ServerResponse(-1, "操作失败", null);
    }

    public static ServerResponse error(int msg, String data) {
        return new ServerResponse(msg, data, null);
    }

    public static ServerResponse error(ResponseEnum responseEnum) {
        //通过返回 枚举类，从而拿到里面定义好的参数
        return new ServerResponse(responseEnum.getCode(), responseEnum.getMsg(), null);
    }

    // 有无参构造  // 私有化是为了只能通过return 接受 ，不能让 new 来获取
    private ServerResponse() {
    }

    private ServerResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
