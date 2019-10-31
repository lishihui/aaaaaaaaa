package com.fh.shop.vip.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.exception.check;
import com.fh.shop.utils.JedisUtils;
import com.fh.shop.utils.KeyUtil;
import com.fh.shop.vip.biz.IVipService;
import com.fh.shop.vip.po.Vip;
import com.fh.shop.vip.po.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin("*")
@RequestMapping("/vips")
public class VipController {
    @Resource(name = "vipService")
    private IVipService vipService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping //验证码
    public ServerResponse smsCode(String phone) {
        return vipService.smsCode(phone);
    }

    @PostMapping
    public ServerResponse add(Vip vip) {
        return vipService.add(vip);
    }

    @PostMapping("/login")
    public ServerResponse login(Vip vip) {
        return vipService.login(vip);
    }

    @PostMapping("/initUser")
    @check//导航条 哪个用户登录
    public ServerResponse initUser() {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        String realName = vipVo.getRealName();
        return ServerResponse.success(realName);
    }

    @PostMapping("/outUser")
    @check//退出
    public ServerResponse outUser() {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        String userName = vipVo.getUserName();
        String uuid = vipVo.getUuid();
        //根据存redis 的key 删除
        JedisUtils.del(KeyUtil.buildVipKey(userName, uuid));
        return ServerResponse.success();
    }

    @PostMapping("/loginPhone")
    public ServerResponse loginPhone(Vip vip, String code) {
        return vipService.loginPhone(vip, code);
    }
}