package com.fh.shop.order.param;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderesParam implements Serializable {
    private Integer payType;            //支付方式  1 微信支付  2  货到付款
}
