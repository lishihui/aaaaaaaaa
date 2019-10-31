package com.fh.shop.vip.biz;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.vip.po.Vip;

public interface IVipService {

    ServerResponse smsCode(String phone);

    ServerResponse add(Vip vip);

    ServerResponse login(Vip vip);

    ServerResponse loginPhone(Vip vip, String code);
}
