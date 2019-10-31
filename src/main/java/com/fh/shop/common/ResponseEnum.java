package com.fh.shop.common;

public enum ResponseEnum {
    PHONE_IS_NULL(1001, "手机号错误"),
    CODE_IS_ERROR(1002, "发送失败"),
    EMAIL_IS_NULL(1003, "邮箱不能为空"),
    REALNAME_IS_NULL(1004, "真实姓名不能为空"),
    CODES_IS_ERROR(1005, "验证码错误"),
    CODE_GUOQI(1006, "验证码已过期"),
    EMAIL_EXRST(1007, "邮箱已存在"),
    PHONE_EXRST(1008, "手机号已存在"),
    REALNAME_EXRST(1009, "真实姓名已存在"),
    USERNAME_ERROR(1010, "账户不存在"),
    USERNAME_IS_NULL(1013, "账户不能为空"),
    PASSWORD_IS_NULL(1014, "密码不能为空"),
    USERNAME_IS_EXRST(1011, "账户不能为空"),
    PASSWORD_ERROR(1012, "密码错误"),
    HEADER_IS_NULL(1013, "头信息丢失"),
    HEADER_IS_ERROR(1014, "头信息错误"),
    HEADER_IS_ATTACK(1015, "头信息被攻击"),
    HEADER_IS_EXISTS(1016, "已超时"),
    SHOP_IS_NULL(1017, "商品信息不存在"),
    SHOP_IS_ISSHELF(1018, "商品已下架"),
    VIP_PHONE_ISNULL(1019, "手机号为空"),
    VIP_NOTE_IS_NULL(1020, "验证码为空"),
    VIP_SMS_ISEXPIRE(1021, "验证码不存在"),
    TOKEN_IS_NULL(1022, "Token头信息不存在"),
    CAT_IS_NULL(1023, "购物车不存在"),
    PAY_IS_NULL(1024, "订单不存在");
    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
