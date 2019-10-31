package com.fh.shop.utils;

public class KeyUtil {

    public static String buildCodeKey(String phone) {
        return "smsCode:" + phone;
    }

    public static String buildVipKey(String userName, String uuid) {
        return "userName:" + userName + ":" + uuid;
    }

    public static String buildCartKey(Long vipId) {
        return "vip:" + vipId;
    }

    public static String buildPayKey(Long vipId) {
        return "pay:" + vipId;
    }
}
