package com.fh.shop.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.cart.vo.Cart;
import com.fh.shop.cart.vo.CartItem;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.common.SystemConstant;
import com.fh.shop.shop.mapper.IShopMappers;
import com.fh.shop.shop.po.Shop;
import com.fh.shop.utils.BigDecimalUtil;
import com.fh.shop.utils.JedisUtils;
import com.fh.shop.utils.KeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
public class ICartServiceImpl implements ICartService {
    @Resource
    private IShopMappers shopMapper;

    @Override
    public ServerResponse addCart(Long shopId, Long count, Long vipId) {
        //根据商品Id 查到此商品对应的信息
        Shop shop = shopMapper.selectById(shopId);
        //判断商品是否存在
        if (shop == null) {
            return ServerResponse.error(ResponseEnum.SHOP_IS_NULL);
        }
        //查看商品是否是上架状态
        if (shop.getIsShelf() != SystemConstant.ISSHELF) {
            return ServerResponse.error(ResponseEnum.SHOP_IS_ISSHELF);
        }
        //判断用户是否有购物车
        String cartJson = JedisUtils.hGet(SystemConstant.CARTNAME, KeyUtil.buildCartKey(vipId));
        if (StringUtils.isEmpty(cartJson)) {
            //没有购物车，则创建一个购物车
            Cart cart = new Cart();
            //往购物车种添加商品
            buildShop(count, shop, cart);
            //更新购物车，返回数据
            updateCart(cart, vipId);
            return ServerResponse.success();
        }
        //有购物车，从redis中取出对应的购物车
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        //
        CartItem cartItem = getShop(shopId, cart);
        //判断商品是否存在,不存在则加入购物车
        if (cartItem == null) {
            buildShop(count, shop, cart);
            //更新数据
            updateCart(cart, vipId);
            return ServerResponse.success();
        }
        //如果商品存在则更新商品以及小计
        cartItem.setCount(cartItem.getCount() + count);
        String subTotalPrice = BigDecimalUtil.multiplyBigDecimal(cartItem.getPrice().toString(), cartItem.getCount().toString()).toString();
        cartItem.setSubTotalPrice(subTotalPrice);//小计
        //更新数据
        updateCart(cart, vipId);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse initQueryList(Long vipId) {
        //判断用户是否有购物车
        String cartJson = JedisUtils.hGet(SystemConstant.CARTNAME, KeyUtil.buildCartKey(vipId));
        if (StringUtils.isEmpty(cartJson)) {
            return ServerResponse.error(ResponseEnum.SHOP_IS_NULL);
        }
        //有的话取出数据
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        return ServerResponse.success(cart);
    }

    //构建商品
    private void buildShop(Long count, Shop shop, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setImage(shop.getImgPath());
        cartItem.setShopName(shop.getShopName());
        cartItem.setShopId(shop.getId());
        BigDecimal price = shop.getPrice();
        cartItem.setPrice(price);
        cartItem.setCount(count);//从前台拿到的count
        //价格和个数相乘 比如两个商品的和
        String subTotalPrice = BigDecimalUtil.multiplyBigDecimal(price.toString(), String.valueOf(count)).toString();
        cartItem.setSubTotalPrice(subTotalPrice);//小计
        if (count < 1) {//判断我的购物车是否还有数据
            cart.getCartItemList().remove(cartItem);
        } else {
            //添加商品
            cart.getCartItemList().add(cartItem);
        }
    }

    //获取商品
    private CartItem getShop(Long shopId, Cart cart) {
        List<CartItem> cartItemList = cart.getCartItemList();
        CartItem shop = null;
        //如果商品不存在的话，则将商品添加到购物车
        for (CartItem cartItem : cartItemList) {
            //如果我购物车里面的id 和我商品的id相等代表是我这个购物车里面的物品
            if (cartItem.getShopId() == shopId) {
                shop = cartItem;
                break;
            }
        }
        return shop;
    }

    //更新商品
    private void updateCart(Cart cart, Long vipId) {
        List<CartItem> cartItemList = cart.getCartItemList();
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
        //更新redis数据
        String cartJson = JSONObject.toJSONString(cart);
        JedisUtils.hSet(SystemConstant.CARTNAME, KeyUtil.buildCartKey(vipId), cartJson);
    }

    //删除
    public ServerResponse delCartProduct(Long shopId, Long vipId) {
        String cartJson = JedisUtils.hGet(SystemConstant.CARTNAME, KeyUtil.buildCartKey(vipId));
        //判断redis中是否有数据
        if (StringUtils.isEmpty(cartJson)) {
            return ServerResponse.error(ResponseEnum.SHOP_IS_NULL);
        }
        //判断商品列表是否有我们需要的数据
        Boolean flag = false;
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> cartItem = cart.getCartItemList();
        for (CartItem item : cartItem) {
            if (item.getShopId() == shopId) {
                cartItem.remove(item);
                flag = true;
                break;
            }
        }
        if (!flag) {
            return ServerResponse.error(ResponseEnum.SHOP_IS_NULL);
        }
        //更新购物车
        cart.setCartItemList(cartItem);
        updateCart(cart, vipId);
        return ServerResponse.success();
    }

}
