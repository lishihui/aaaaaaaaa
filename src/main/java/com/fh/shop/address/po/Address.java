package com.fh.shop.address.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_address")
public class Address implements Serializable {
    @TableId(type = IdType.INPUT)
    private String addressId;          //地址Id
    private String vipName;            //会员名
    private Integer sheng;
    private Integer shi;
    private Integer xian;
    @TableField(exist = false)// 不对应数据库,前台展示用
    private String areaName;
    private String addressee;          //详细地址
    private String addresseePhone;     //收货人电话
    private String addresseeName;      //收货人姓名
    private String defaultAddress;     //默认地址


}
