package com.fh.shop.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.exception.GlobalException;
import com.fh.shop.utils.JedisUtils;
import com.fh.shop.utils.KeyUtil;
import com.fh.shop.utils.Md5Util;
import com.fh.shop.vip.po.VipVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

public class MyInterceptor2 extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //获取头信息
        String header = request.getHeader("x-auth");
        if (StringUtils.isEmpty(header)) {
            throw new GlobalException(ResponseEnum.HEADER_IS_NULL);
        }
        //将其进行分割
        String[] split = header.split("\\.");
        if (split.length == 2) {
            throw new GlobalException(ResponseEnum.HEADER_IS_ATTACK);
        }
        String encode = split[0];
        String newEncode = split[1];
        //进行解码
        String encodeStr = new String(Base64.getDecoder().decode(encode));
        String encode1 = Base64.getEncoder().encodeToString(encodeStr.getBytes());
        //解码之后进行加密，
        String Md5Encode = Md5Util.buildSign(encode1, SystemConstant.SECRET);
        String encode1s = Base64.getEncoder().encodeToString(Md5Encode.getBytes());
        if (!newEncode.equals(encode1s)) {
            throw new GlobalException(ResponseEnum.HEADER_IS_ERROR);
        }
        //将json 格式的字符串转换成对象，从而获取到里面的数据
        VipVo vipVo = JSONObject.parseObject(encodeStr, VipVo.class);
        String userName = vipVo.getUserName();
        String uuid = vipVo.getUuid();
        //查看是否过期
        Boolean exists = JedisUtils.exists(KeyUtil.buildVipKey(userName, uuid));
        if (!exists) {
            throw new GlobalException(ResponseEnum.HEADER_IS_EXISTS);
        }
        //重新设置存活时间
        JedisUtils.setSeconds(KeyUtil.buildVipKey(userName, uuid), SystemConstant.CODE_EXPIRE, "snbbwpisg");
        return true;
    }

}
