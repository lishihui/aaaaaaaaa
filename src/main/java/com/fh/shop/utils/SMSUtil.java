package com.fh.shop.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SMSUtil {
    private static String URI = "https://api.netease.im/sms/sendcode.action";//网易云信地址
    private static String APPKEY = "7545095dea3d8436184cd3673e24a57e";//账号
    private static String APPSECRET = "e2ee0dde60d9";//密码


    public static String postSMS() {
        //传递参数集合
        Map<String, String> heads = new HashMap<>();
        //传递header
        heads.put("AppKey", "541408ee41373593befbbe3d2b3c27fa");
        String nonce = UUID.randomUUID().toString();
        heads.put("Nonce", nonce);
        String curTime = System.currentTimeMillis() + "";
        heads.put("CurTime", curTime);
        heads.put("CheckSum", CheckSumBuilder.getCheckSum("d23d0ea733f7", nonce, curTime));
        //传递的参数
        Map<String, String> basicName = new HashMap<>();
        basicName.put("mobile", "15136302759");
        String result = HttpClientUtil.sentHttpClient(URI, heads, basicName);
        return result;
    }

    public static String sentSMS(String phone) {
        //传递参数集合
        Map<String, String> heads = new HashMap<>();
        //传递header
        heads.put("AppKey", APPKEY);
        String nonce = UUID.randomUUID().toString();
        heads.put("Nonce", nonce);
        String curTime = System.currentTimeMillis() + "";
        heads.put("CurTime", curTime);
        heads.put("CheckSum", CheckSumBuilder.getCheckSum(APPSECRET, nonce, curTime));
        //传递的参数
        Map<String, String> basicName = new HashMap<>();
        basicName.put("mobile", phone);
        //验证码长度
        basicName.put("codeLen", "6");
        String result = HttpClientUtil.sentHttpClient(URI, heads, basicName);
        return result;
    }
}
