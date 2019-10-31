package com.fh.shop.cart.controller;

import com.fh.shop.cart.biz.ICartService;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.exception.check;
import com.fh.shop.vip.po.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Resource(name = "cartService")
    private ICartService cartService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping
    @check
    public ServerResponse addCart(Long shopId, Long count) {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        Long vipId = vipVo.getId();
        return cartService.addCart(shopId, count, vipId);
    }

    @PostMapping("/initQueryList")
    @check
    public ServerResponse initQueryList() {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        Long vipId = vipVo.getId();
        return cartService.initQueryList(vipId);
    }

    @PostMapping("/delCartProduct")
    @check
    public ServerResponse delCartProduct(Long shopId) {
        VipVo vipVo = (VipVo) request.getAttribute("vipVo");
        Long vipId = vipVo.getId();
        return cartService.delCartProduct(shopId, vipId);
    }
}
