package com.fh.shop.address.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class AddressVo implements Serializable {
    private String addressId;          //地址Id
    private String vipName;            //会员名
    private String areaName;        // 三级联动名字
    private String addressee;          //详细地址
    private String addresseePhone;     //收货人电话
    private String addresseeName;      //收货人姓名
    private String defaultAddress;     //默认地址


}
