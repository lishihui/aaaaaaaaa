package com.fh.shop.order.biz;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.order.param.OrderesParam;

public interface IOrderesService {
    ServerResponse addOrderes(OrderesParam orderesParam, Long vipId);

    void test();
}
