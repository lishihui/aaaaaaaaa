package com.fh.shop.brand.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.brand.mapper.IBrandMapper;
import com.fh.shop.brand.po.Brand;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {
    @Resource
    private IBrandMapper brandMapper;


    @Override
    public ServerResponse queryBrandList() {
        String brandListJson = JedisUtils.get("brandList");
        //不为空的话，返回数据
        if (StringUtils.isNotEmpty(brandListJson)) {
            List<Brand> brands = JSONObject.parseArray(brandListJson, Brand.class);
            return ServerResponse.success(brands);
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("sort");
        wrapper.eq("popular", "0");
        List<Brand> brandList = brandMapper.selectList(wrapper);
        String toJSONString = JSONObject.toJSONString(brandList);
        JedisUtils.set("brandList", toJSONString);
        return ServerResponse.success(brandList);
    }
}
