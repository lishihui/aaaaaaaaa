package com.fh.shop.shop.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.shop.mapper.IShopMappers;
import com.fh.shop.shop.po.Shop;
import com.fh.shop.shop.vo.ShopVo;
import com.fh.shop.utils.DateAndString;
import com.fh.shop.utils.JedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("shopService")
public class IShopServiceImpl implements IShopService {
    @Resource
    private IShopMappers shopMapper;

    @Override
    public ServerResponse queryList() {
        String shopListJson = JedisUtils.get("shopList");
        if (StringUtils.isNotEmpty(shopListJson)) {
            List<ShopVo> shopVoList = JSONObject.parseArray(shopListJson, ShopVo.class);
            return ServerResponse.success(shopVoList);
        }
        QueryWrapper query = new QueryWrapper();
        query.orderByDesc("id");
        query.eq("isShelf", "0");
        List<Shop> shopList = shopMapper.selectList(query);
        List<ShopVo> shopVoList = buildVo(shopList);
        String toJSONString = JSONObject.toJSONString(shopVoList);
        JedisUtils.setSeconds("shopList", 30, toJSONString);
        return ServerResponse.success(shopVoList);
    }

    private List<ShopVo> buildVo(List<Shop> shopList) {
        List<ShopVo> shopVoList = new ArrayList<>();
        for (Shop shop : shopList) {
            ShopVo vo = new ShopVo();
            vo.setShopName(shop.getShopName());
            vo.setPrice(shop.getPrice().toString());
            vo.setId(shop.getId());
            vo.setBrandId(shop.getBrandId());
            vo.setProductiveTime(DateAndString.date2String(shop.getProductiveTime(), DateAndString.Y_M_D));
            vo.setStock(shop.getStock());
            vo.setImgPath(shop.getImgPath());
            vo.setIsHot(shop.getIsHot());
            vo.setIsShelf(shop.getIsShelf());
            shopVoList.add(vo);
        }
        return shopVoList;
    }
}
