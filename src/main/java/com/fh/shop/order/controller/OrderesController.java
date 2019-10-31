package com.fh.shop.order.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.exception.Idempotent;
import com.fh.shop.exception.check;
import com.fh.shop.order.biz.IOrderesService;
import com.fh.shop.order.param.OrderesParam;
import com.fh.shop.vip.po.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/orderes")
public class OrderesController {
    @Resource(name = "orderesService")
    private IOrderesService orderesService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping
    @check
    @Idempotent
    public ServerResponse addOrderes(OrderesParam orderesParam) {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        Long vipId = vipVo.getId();
        return orderesService.addOrderes(orderesParam, vipId);
    }
}
