package com.fh.shop.address.biz;

import com.fh.shop.address.po.Address;
import com.fh.shop.common.ServerResponse;

public interface IAddressService {
    ServerResponse queryAddress();

    ServerResponse addAddress(Address address);

    ServerResponse toupdate(String addressId);

    ServerResponse updateAddress(Address address);
}
