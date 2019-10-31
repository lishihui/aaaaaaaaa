package com.fh.shop.address.biz;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.address.mapper.IAddressMappers;
import com.fh.shop.address.po.Address;
import com.fh.shop.address.vo.AddressVo;
import com.fh.shop.common.ServerResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("addressService")
public class IAddressServiceImpl implements IAddressService {
    @Resource
    private IAddressMappers addressMapper;

    @Override
    public ServerResponse queryAddress() {
        List<Address> addressList = addressMapper.queryAddress();
        List<AddressVo> addressVoList = new ArrayList<>();
        for (Address address : addressList) {
            AddressVo addressVo = new AddressVo();
            addressVo.setVipName(address.getVipName());
            addressVo.setAddressee(address.getAddressee());
            addressVo.setAddresseeName(address.getAddresseeName());
            addressVo.setAddresseePhone(address.getAddresseePhone());
            addressVo.setAddressId(address.getAddressId());
            addressVo.setAreaName(address.getAreaName());
            addressVo.setDefaultAddress(address.getDefaultAddress());
            addressVoList.add(addressVo);
        }
        return ServerResponse.success(addressVoList);
    }

    @Override
    public ServerResponse addAddress(Address address) {
        address.setDefaultAddress("1");
        address.setAddressId(IdWorker.getTimeId());
        addressMapper.insert(address);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse toupdate(String addressId) {
        Address address = addressMapper.toupdate(addressId);
        return ServerResponse.success(address);
    }

    @Override
    public ServerResponse updateAddress(Address address) {
        addressMapper.updateAddress(address);
        return ServerResponse.success();
    }
}
