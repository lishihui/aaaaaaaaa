package com.fh.shop.area.controller;

import com.fh.shop.area.biz.IAreaService;
import com.fh.shop.area.po.Area;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.exception.check;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@CrossOrigin("*")
@RequestMapping("/areas")
public class AreaController {
    @Resource(name = "areaService")
    private IAreaService areaService;

    @GetMapping
    public ServerResponse queryAreaList() {
        return areaService.queryAreaList();
    }

    @PostMapping
    public ServerResponse add(Area area) {
        return areaService.add(area);
    }

    @DeleteMapping(value = "/{id}")
    public ServerResponse del(@PathVariable Long id) {
        return areaService.del(id);
    }

    @GetMapping(value = "/{id}")
    public ServerResponse queryById(@PathVariable Long id) {
        return areaService.queryById(id);
    }

    @PutMapping
    public ServerResponse update(@RequestBody Area area) {
        return areaService.update(area);
    }

    @DeleteMapping
    public ServerResponse delBatch(String ids) {
        return areaService.delBatch(ids);
    }

    @GetMapping(value = "/queryArea")
    @check
    public ServerResponse queryArea(Long id) {
        return areaService.queryArea(id);
    }
}
