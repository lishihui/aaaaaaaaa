package com.fh.shop.paylog.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Paylog implements Serializable {
    @TableId(value = "out_trade_no", type = IdType.INPUT)
    private String outTradeNo;  //支付订单Id
    private Long vipId;       //会员Id
    private String orderId;     //订单Id
    private String transactionId;  //交易流水号
    private Date createTime;  //创建时间
    private Date payTime;     //支付时间
    private BigDecimal payMoney;//支付金额
    private Integer payType;    //支付平台  1支付宝 2微信
    private Integer payStatus;  //支付状态  1已经支付   2没有支付 3退款

    @TableField(exist = false)// 不对应数据库,前台展示用
    private String codeUrl;

}
