package com.fh.shop.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void writeCookie(String name, String value, String domain, HttpServletResponse response) {
        //创建一个cookie会话  name代表sessionId value代表sessionId对应的数据
        Cookie cookie = new Cookie(name, value);
        //新建域名
        cookie.setDomain(domain);
        //网站根目录
        cookie.setPath("/");
        //将创建好的cookie会话写入到客户端cookie内存中
        response.addCookie(cookie);
    }

    public static String readCookie(String name, HttpServletRequest request) {
        //获取存在客户端中的所有cookie会话
        Cookie[] cookies = request.getCookies();
        //首先判断cookie会话不为空
        if (cookies == null) {
            return "";
        }
        //通过客户端中的cookie会话获取sessionId指定的数据
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return "";
    }
}
