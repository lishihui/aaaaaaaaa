package com.fh.shop.address.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.address.po.Address;

import java.util.List;

public interface IAddressMappers extends BaseMapper<Address> {
    List<Address> queryAddress();

    Address toupdate(String addressId);

    void updateAddress(Address address);
}
