package com.bosssoft.cartdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.bosssoft.cartdemo.entity.Goods;
import com.bosssoft.cartdemo.service.CartService;
import com.bosssoft.cartdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    OrderService orderService;

    @Autowired
    private HttpSession session;

    @Override
    public boolean add(Goods goods) {
        HashMap<Long, Goods> mycart = getCart();
        mycart.put(goods.getGoodsId(), goods);
        return true;
    }

    @Override
    public boolean edit(long goodsId, int number) {
        HashMap<Long, Goods> mycart = getCart();
        Goods goods = mycart.get(goodsId);
        if (goods == null) {
            return false;
        }
        goods.setNumber(number);
        return true;
    }

    @Override
    public void remove(long goodsId) {
        HashMap<Long, Goods> mycart = getCart();
        mycart.remove(goodsId);
    }

    @Override
    public void clear() {
        HashMap<Long, Goods> mycart = getCart();
        mycart.clear();
    }

    @Override
    public String list() {
        HashMap<Long, Goods> mycart = getCart();
        return JSON.toJSONString(mycart.entrySet().toArray());
    }

    @Override
    public String settle() {
        if (orderService.settleCart(getUserId())){
            clear();
            return "Cart Service Settled";
        }
        return "error";
    }

    @Override
    public Long getUserId() {
        return (Long) session.getAttribute("userId");
    }

    @Override
    public HashMap<Long, Goods> getCart() {
        HashMap<Long, Goods> mycart = (HashMap<Long, Goods>) session.getAttribute("mycart");
        if (mycart == null) {
            mycart = new HashMap<>();
            session.setAttribute("mycart", mycart);
        }
        return mycart;
    }

}
