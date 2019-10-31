package com.fh.shop.shop.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.shop.biz.IShopService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/shop")
public class ShopController {
    @Resource(name = "shopService")
    private IShopService shopService;

    @RequestMapping("/queryList")
    public ServerResponse queryList() {
        ServerResponse shopList = shopService.queryList();
        return shopList;
    }
}
