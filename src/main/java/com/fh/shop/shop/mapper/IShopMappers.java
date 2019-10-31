package com.fh.shop.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.shop.po.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IShopMappers extends BaseMapper<Shop> {
    Long updateStock(@Param("shopId") Long shopId, @Param("count") Long count);

    List<Shop> queryList();

}
