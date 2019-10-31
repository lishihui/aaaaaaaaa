package com.fh.shop.utils;

import com.github.wxpay.sdk.WXPay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WxUtil {

    public static Map<String, String> getQBCode(String outTradeNo, BigDecimal payMoney) {
        MyConfig config = null;
        Map<String, String> resp = null;
        try {
            config = new MyConfig();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "腾讯充值中心-QQ会员充值");
            data.put("out_trade_no", outTradeNo);//订单号
            data.put("total_fee", BigDecimalUtil.getMoney((BigDecimalUtil.multiplyBigDecimal(payMoney + "", "1")) + ""));//价格
            /* data.put("total_fee",payMoney.intValue()+"");//价格*/
            data.put("fee_type", "CNY");
            data.put("spbill_create_ip", "123.12.12.123");
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", IDUtil.createId());
            resp = wxpay.unifiedOrder(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
}
