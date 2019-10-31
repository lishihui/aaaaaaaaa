package com.fh.shop.address.controller;

import com.fh.shop.address.biz.IAddressService;
import com.fh.shop.address.po.Address;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.exception.check;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Resource(name = "addressService")
    private IAddressService addressService;


    @GetMapping
    @check
    public ServerResponse queryAddress() {
        return addressService.queryAddress();
    }

    @PostMapping
    public ServerResponse addAddress(Address address) {
        return addressService.addAddress(address);
    }

    @PostMapping("/toupdate")
    public ServerResponse toupdate(String addressId) {
        return addressService.toupdate(addressId);
    }

    @PutMapping
    public ServerResponse updateAddress(@RequestBody Address address) {
        return addressService.updateAddress(address);
    }


}
