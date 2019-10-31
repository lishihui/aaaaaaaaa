package com.fh.shop.utils;

import com.fh.shop.common.SystemConstant;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class DistributedSession {


    public static String getSession(HttpServletRequest request, HttpServletResponse response) {
        //sessionId
        String sessionId = CookieUtil.readCookie(SystemConstant.COOKIE_NAME, request);
        if (StringUtils.isEmpty(sessionId)) {
            //生成sessionID
            sessionId = UUID.randomUUID().toString();
            //写到客户端浏览器中
            CookieUtil.writeCookie(SystemConstant.COOKIE_NAME, sessionId, SystemConstant.DOMAIN, response);
        }
        return sessionId;
    }
}
