package com.fh.shop.cart.biz;

import com.fh.shop.common.ServerResponse;

public interface ICartService {
    ServerResponse addCart(Long shopId, Long count, Long vipId);

    ServerResponse initQueryList(Long vipId);

    ServerResponse delCartProduct(Long shopId, Long vipId);
}
