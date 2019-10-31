package com.fh.shop.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.cart.vo.Cart;
import com.fh.shop.cart.vo.CartItem;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.order.mapper.IOrderItemMapper;
import com.fh.shop.order.mapper.IOrderesMapper;
import com.fh.shop.order.param.OrderesParam;
import com.fh.shop.order.po.OrderItem;
import com.fh.shop.order.po.Orderes;
import com.fh.shop.paylog.mapper.IPaylogMapper;
import com.fh.shop.paylog.po.Paylog;
import com.fh.shop.shop.mapper.IShopMappers;
import com.fh.shop.shop.po.Shop;
import com.fh.shop.utils.BigDecimalUtil;
import com.fh.shop.utils.IDUtil;
import com.fh.shop.utils.JedisUtils;
import com.fh.shop.utils.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("orderesService")
@Component
@EnableScheduling
public class IOrderesServiceImpl implements IOrderesService {

    @Resource
    private IOrderesMapper orderesMapper;
    @Resource
    private IOrderItemMapper orderItemMapper;
    @Resource
    private IShopMappers shopMapper;
    @Resource
    private IPaylogMapper paylogMapper;


    @Override
    public ServerResponse addOrderes(OrderesParam orderesParam, Long vipId) {
        //从redis 中取出购物车的信息
        String cartJson = JedisUtils.hGet(SystemConstant.CARTNAME, KeyUtil.buildCartKey(vipId));
        //判断非空
        if (StringUtils.isEmpty(cartJson)) {
            return ServerResponse.error(ResponseEnum.SHOP_IS_NULL);
        }
        //转换成 购物车
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        //取出购物车中的商品
        List<CartItem> cartItemList = cart.getCartItemList();
        //创建一个空数组存 库存不够的商品
        List<CartItem> newList = new ArrayList<>();
        //购物车添加商品
        for (int i = cartItemList.size() - 1; i >= 0; i--) {
            //购物车中一个一个商品
            CartItem cartItem = cartItemList.get(i);
            //商品信息
            OrderItem orderItem = new OrderItem();
            orderItem.setShopName(cartItem.getShopName());
            Long count = cartItem.getCount();
            orderItem.setShopCount(count);
            orderItem.setShopPrice(cartItem.getPrice());
            orderItem.setShopSubTotalPrice(new BigDecimal(cartItem.getSubTotalPrice()));
            orderItem.setShopImg(cartItem.getImage());
            orderItem.setVipId(vipId);
            orderItem.setOrderId(IdWorker.getTimeId());
            orderItem.setShopId(cartItem.getShopId());
            //获取到商品的Id ，以及商品的库存
            Long shopId = orderItem.getShopId();
            Shop shop = shopMapper.selectById(shopId);
            Long stock = shop.getStock();
            //如果库存够，就进行修改
            if (stock >= count) {
                //乐观锁，解决高病发
                Long aLong = shopMapper.updateStock(shopId, count);
                //根据修改执行的结果，判断是否执行成功
                if (aLong == 0) {
                    //0 行受到影响， 则代表数据没有更改成功
                    //库存不够添加到空的数组里面
                    newList.add(cartItem);
                    //把空的商品移除
                    cartItemList.remove(cartItem);
                } else {
                    // 1 行数据受到影响，成功
                    orderItemMapper.insert(orderItem);
                }
            } else {
                //库存不够添加到空的数组里面
                newList.add(cartItem);
                //把空的商品移除
                cartItemList.remove(cartItem);
            }
            //默认为0，在这个基础上进行添加
            Long totalCount = 0L;
            BigDecimal totalPrice = new BigDecimal(0);
            for (CartItem item : cartItemList) {
                //每次把前台传过来的数据进行增加，
                totalCount += item.getCount();
                //把新值附上去
                totalPrice = BigDecimalUtil.addBigDecimal(totalPrice.toString(), item.getSubTotalPrice());
            }
            cart.setTotalCount(totalCount);//设置购物车的总个数
            cart.setTotalPrice(totalPrice.toString());//总价格
        }
        //添加订单
        Orderes orderes = new Orderes();
        orderes.setOrderTotalPrice(new BigDecimal(cart.getTotalPrice()));
        orderes.setOrderTotalCount(cart.getTotalCount());
        orderes.setVipId(vipId);
        orderes.setPayType(1);
        orderes.setId(IdWorker.getTimeId());
        orderes.setAddressee("t");
        orderes.setAddresseePhone("13329438295");
        orderes.setAddresseeName("是谁");
        orderesMapper.insert(orderes);
        /* 支付日志表 */
        Paylog paylog = new Paylog();
        paylog.setVipId(vipId);
        paylog.setOutTradeNo(IDUtil.createId());
        paylog.setCreateTime(new Date());
        paylog.setOrderId(orderes.getId());
        paylog.setPayMoney(orderes.getOrderTotalPrice());
        paylog.setPayType(SystemConstant.PAYTYPE);
        paylogMapper.insert(paylog);
        //将日志信息存入redis中
        String payJson = JSONObject.toJSONString(paylog);
        JedisUtils.set(KeyUtil.buildPayKey(vipId), payJson);
        JedisUtils.hDel(SystemConstant.CARTNAME, KeyUtil.buildCartKey(vipId));
        return ServerResponse.success(newList);
    }


    @Scheduled(cron = "*/200 * * * * ?")//每隔5秒执行一次
    public void test() {
        List<Shop> shopList = shopMapper.queryList();
        String table = "";
        table += "<table border='1'>";
        for (Shop shop : shopList) {
            table += "<tr>\n" +
                    "        <td>商品</td>\n" +
                    "        <td>" + shop.getShopName() + "</td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td>库存</td>\n" +
                    "        <td>" + shop.getStock() + "</td>\n" +
                    "    </tr>\n" +
                    "    <tr>" +
                    "        <td>价格</td>" +
                    "        <td>" + shop.getPrice() + "</td>" +
                    "</tr>";
        }
        table += "</table>";
        //MailUtil.bulidMail("库存不够",table,"1374232507@qq.com");
    }
}
