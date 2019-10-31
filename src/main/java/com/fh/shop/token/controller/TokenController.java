package com.fh.shop.token.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.exception.check;
import com.fh.shop.token.biz.ITokenService;
import com.fh.shop.vip.po.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Resource(name = "tokenService")
    private ITokenService tokenService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping
    @check
    public ServerResponse createToken() {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        Long vipVoId = vipVo.getId();
        return tokenService.createToken(vipVoId);
    }
}
