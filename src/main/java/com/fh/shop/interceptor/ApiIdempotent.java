package com.fh.shop.interceptor;

import com.fh.shop.common.ResponseEnum;
import com.fh.shop.exception.GlobalException;
import com.fh.shop.exception.Idempotent;
import com.fh.shop.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ApiIdempotent extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //获取方法签名
        Method method = handlerMethod.getMethod();
        //判断方法上面是否加入自定义注解
        if (!method.isAnnotationPresent(Idempotent.class)) {
            return true;
        }
        //头信息
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new GlobalException(ResponseEnum.TOKEN_IS_NULL);
        }
        //判断头信息是否存在，会返回一个 boolean类型
        Boolean exists = JedisUtils.exists(token);
        if (!exists) {
            throw new GlobalException(ResponseEnum.HEADER_IS_NULL);
        }
        //删除redis  删除成功会返回1， 没有的话会返回0
        Long aLong = JedisUtils.delToken(token);
        if (aLong <= 0) {
            throw new GlobalException(ResponseEnum.HEADER_IS_EXISTS);
        }
        return true;
    }
}
