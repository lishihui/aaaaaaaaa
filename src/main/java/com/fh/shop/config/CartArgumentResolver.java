package com.fh.shop.config;

import com.fh.shop.vip.po.VipVo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class CartArgumentResolver implements HandlerMethodArgumentResolver {
    //参数解析器
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //获取参数类型
        Class<?> parameterType = parameter.getParameterType();
        //判断传过来的参数和在类上加入的参数是否相等，如果一致才执行下面的方法
        return parameterType == VipVo.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //根据我传入的类，返回我传入的类
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        return vipVo;
    }
}
