package com.bosssoft.cartdemo.service.impl;

import com.bosssoft.cartdemo.entity.Goods;
import com.bosssoft.cartdemo.service.CartService;
import com.bosssoft.cartdemo.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    OrderService orderService;

    @Autowired
    private HttpSession session;

    @Override
    public void add(Goods goods) {
        HashMap<Long, Goods> myCart = getCart();
        myCart.put(goods.getGoodsId(), goods);
    }

    @Override
    public boolean edit(long goodsId, int number) {
        HashMap<Long, Goods> myCart = getCart();
        Goods goods = myCart.get(goodsId);
        if (goods == null) {
            log.info("购物车不存在该商品");
            return false;
        }
        goods.setNumber(number);
        return true;
    }

    @Override
    public void remove(long goodsId) {
        HashMap<Long, Goods> myCart = getCart();
        myCart.remove(goodsId);
    }

    @Override
    public void clear() {
        HashMap<Long, Goods> myCart = getCart();
        myCart.clear();
    }

    @Override
    public boolean settle() {
        if (orderService.settleCart(getUserId())){
            clear();
            return true;
        }
        return false;
    }

    @Override
    public Long getUserId() {
        return (Long) session.getAttribute("userId");
    }

    @Override
    public HashMap<Long, Goods> getCart() {
        HashMap<Long, Goods> myCart = (HashMap<Long, Goods>) session.getAttribute("myCart");
        if (myCart == null) {
            log.info("新建购物车");
            myCart = new HashMap<>();
            session.setAttribute("myCart", myCart);
        }
        return myCart;
    }

}
