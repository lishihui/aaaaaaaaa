package com.fh.shop.brand.controller;

import com.fh.shop.brand.biz.IBrandService;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Resource(name = "brandService")
    private IBrandService brandService;

    @RequestMapping("/queryBrandList")
    public ServerResponse queryBrandList() {
        ServerResponse list = brandService.queryBrandList();
        return list;
    }

}
