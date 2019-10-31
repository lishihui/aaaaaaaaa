package com.fh.shop.classify.controller;

import com.fh.shop.classify.biz.IClassifyService;
import com.fh.shop.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/classify")
public class ClassifyController {
    @Resource(name = "classifyService")
    private IClassifyService classifyService;


    @GetMapping()
    @CrossOrigin("*")
    public ServerResponse queryClassifyList() {
        return classifyService.queryClassifyList();
    }
}
