package com.fh.shop.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.exception.GlobalException;
import com.fh.shop.exception.check;
import com.fh.shop.utils.JedisUtils;
import com.fh.shop.utils.KeyUtil;
import com.fh.shop.utils.Md5Util;
import com.fh.shop.vip.po.VipVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class MyInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //处理跨域
        //头信息
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "x-auth,token,content-type,x");
        response.addHeader("Access-Control-Allow-Methods", "DELETE,POST,GET,PUT");
        //处理 option请求 ，默认会发送一个options 请求，不带参数
        String options = request.getMethod();
        if (options.equalsIgnoreCase("options")) {
            return false;
        }
        //判断方法上面是否加入自定义注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //通过方法签名得到 method
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(check.class)) {
            return true;
        }
        //获取头信息
        String header = request.getHeader("x-auth");
        if (StringUtils.isEmpty(header)) {
            throw new GlobalException(ResponseEnum.HEADER_IS_NULL);
        }
        //按保存的时候 . 进行分割，分别取到两个值
        String[] split = header.split("\\.");
        if (split.length != 2) {
            throw new GlobalException(ResponseEnum.HEADER_IS_ATTACK);
        }
        String encode = split[0];
        String newEncode = split[1];
        //进行解析 将其转换为 String类型的字符串
        String encodeJson = new String(Base64.getDecoder().decode(encode));
        //进行加密，和另外一个值进行对比
        String encodes = Base64.getEncoder().encodeToString(encodeJson.getBytes());
        String encodeMd5 = Md5Util.buildSign(encodes, SystemConstant.SECRET);
        String decode = Base64.getEncoder().encodeToString(encodeMd5.getBytes());
        if (!newEncode.equals(decode)) {
            throw new GlobalException(ResponseEnum.HEADER_IS_ERROR);
        }
        //转换成对象，从而获取到uuid 和 账户名称
        VipVo vipVo = JSONObject.parseObject(encodeJson, VipVo.class);
        //获取 uuid
        String uuid = vipVo.getUuid();
        String userName = vipVo.getUserName();
        //从redis 中取出 对应的数据
        Boolean exists = JedisUtils.exists(KeyUtil.buildVipKey(userName, uuid));
        //判断是否过了超时时间
        if (!exists) {
            throw new GlobalException(ResponseEnum.HEADER_IS_EXISTS);
        }
        //续命
        JedisUtils.setSeconds(KeyUtil.buildVipKey(userName, uuid), SystemConstant.CODE_EXPIRE, "tiantian");
        //将数据存到request中，当前请求
        request.setAttribute("vipVo", vipVo);
        return true;
    }

}
