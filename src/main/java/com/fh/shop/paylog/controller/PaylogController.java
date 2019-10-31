package com.fh.shop.paylog.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.exception.check;
import com.fh.shop.paylog.biz.IPaylogService;
import com.fh.shop.vip.po.VipVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pay")
public class PaylogController {
    @Resource(name = "payLogService")
    private IPaylogService paylogService;
    @Resource
    private HttpServletRequest request;

    @GetMapping
    @check
    public ServerResponse createPay() {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        Long vipVoId = vipVo.getId();
        return paylogService.createPay(vipVoId);
    }

    @PostMapping
    @check
    public ServerResponse queryPayStatus() {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        Long vipVoId = vipVo.getId();
        return paylogService.queryPayStatus(vipVoId);
    }

}
