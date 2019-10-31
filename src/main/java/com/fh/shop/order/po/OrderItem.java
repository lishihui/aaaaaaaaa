package com.fh.shop.order.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("t_order_item")
public class OrderItem implements Serializable {
    //订单详情表
    private String orderId;        //订单id 外键
    private Long shopId;         //商品id  外键
    private String shopName;       //商品名
    private BigDecimal shopPrice;      //单个商品价格
    private Long shopCount;      //用户已选中商品的数量
    private BigDecimal shopSubTotalPrice;//商品的小计   价格
    private String shopImg;        //商品图片
    private Long vipId;          //会员id    冗余参数/属性


}
