package com.fh.shop.paylog.biz;

import com.fh.shop.common.ServerResponse;

public interface IPaylogService {
    ServerResponse createPay(Long vipVoId);

    ServerResponse queryPayStatus(Long vipVoId);
}
