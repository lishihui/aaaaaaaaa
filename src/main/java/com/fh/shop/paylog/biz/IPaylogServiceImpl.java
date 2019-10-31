package com.fh.shop.paylog.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.order.mapper.IOrderesMapper;
import com.fh.shop.order.po.Orderes;
import com.fh.shop.paylog.mapper.IPaylogMapper;
import com.fh.shop.paylog.po.Paylog;
import com.fh.shop.utils.JedisUtils;
import com.fh.shop.utils.KeyUtil;
import com.fh.shop.utils.MyConfig;
import com.fh.shop.utils.WxUtil;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payLogService")
public class IPaylogServiceImpl implements IPaylogService {
    @Resource
    private IPaylogMapper paylogMapper;
    @Resource
    private IOrderesMapper orderesMapper;

    @Override
    public ServerResponse createPay(Long vipVoId) {
        //先取出订单，判断订单是否存在
        String payJson = JedisUtils.get(KeyUtil.buildPayKey(vipVoId));
        if (StringUtils.isEmpty(payJson)) {
            return ServerResponse.error(ResponseEnum.PAY_IS_NULL);
        }
        //转成对象
        Paylog paylog = JSONObject.parseObject(payJson, Paylog.class);
        String outTradeNo = paylog.getOutTradeNo();
        BigDecimal payMoney = paylog.getPayMoney();
        //
        Map<String, String> map = WxUtil.getQBCode(outTradeNo, payMoney);
        String return_code = map.get("return_code");
        String return_msg = map.get("return_msg");
        if (!return_code.equalsIgnoreCase("SUCCESS")) {
            return ServerResponse.error(9999, "微信错误提示：" + return_msg);
        }
        String result_code = map.get("result_code");
        String result_msg = map.get("result_msg");
        if (!result_code.equalsIgnoreCase(result_code)) {
            return ServerResponse.error(9999, "微信错误提示：" + result_msg);
        }
        Paylog paylog1 = new Paylog();
        //将信息返回给前台
        String code_url = map.get("code_url");
        paylog1.setCodeUrl(code_url);
        paylog1.setOrderId(paylog.getOrderId());
        paylog1.setPayMoney(paylog.getPayMoney());
        return ServerResponse.success(paylog1);
    }

    @Override
    public ServerResponse queryPayStatus(Long vipVoId) {
        //先取出订单，判断订单是否存在
        String payJson = JedisUtils.get(KeyUtil.buildPayKey(vipVoId));
        if (StringUtils.isEmpty(payJson)) {
            return ServerResponse.error(ResponseEnum.PAY_IS_NULL);
        }
        //转成对象
        Paylog paylog = JSONObject.parseObject(payJson, Paylog.class);
        String orderId = paylog.getOrderId();
        try {
            MyConfig config = new MyConfig();
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            String outTradeNo = paylog.getOutTradeNo();
            data.put("out_trade_no", outTradeNo);
            /*定义一个初始值*/
            int count = 0;
            while (true) {
                Map<String, String> resp = wxpay.orderQuery(data);
                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                if (!return_code.equalsIgnoreCase("SUCCESS")) {
                    System.out.println(return_msg);
                    return ServerResponse.error(9999, "微信错误提示：" + return_msg);
                }
                String result_code = resp.get("result_code");
                String result_msg = resp.get("result_msg");
                if (!result_code.equalsIgnoreCase("SUCCESS")) {
                    System.out.println(result_msg);
                    return ServerResponse.error(9999, "微信错误提示：" + result_msg);
                }
                String trade_state = resp.get("trade_state");
                if (trade_state.equalsIgnoreCase("SUCCESS")) {
                    String transaction_id = resp.get("transaction_id");
                    /*日志订单表插入支付成功的数据*/
                    Paylog paylog1 = paylogMapper.selectById(outTradeNo);
                    Date date = new Date();
                    paylog1.setPayTime(date);
                    paylog1.setOutTradeNo(outTradeNo);
                    paylog1.setTransactionId(transaction_id);
                    paylog1.setPayStatus(20);
                    paylogMapper.updateById(paylog1);
                    /*往订单表插入支付成功的数据*/
                    Orderes orderes = orderesMapper.selectById(orderId);
                    orderes.setOrderPayTime(date);
                    orderes.setOrderStatus(20);
                    orderes.setOrderWinTime(date);
                    orderesMapper.updateById(orderes);
                    return ServerResponse.success();
                }
                count++;
                Thread.sleep(500);
                if (count > 100) {
                    System.out.println("支付超时");
                    return ServerResponse.error(9999, "支付超时");
                }
                System.out.println("用户未支付");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }


}
