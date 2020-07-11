package com.bosssoft.cartdemo.service;

import com.alibaba.fastjson.JSON;
import com.bosssoft.cartdemo.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Service
public class CartService {
    private HashMap<Long, Goods> mycart;

    @Autowired
    OrderService orderService;

    @Autowired
    private HttpSession session;

    public void add(Goods goods) {
        getCart();
        mycart.put(goods.getGoodsId(), goods);
    }

    public boolean edit(long goodsId, int number) {
        getCart();
        Goods goods = mycart.get(goodsId);
        if (number > goods.getTotal()) {
            return false;
        }
        goods.setNumber(number);
        return true;
    }

    public void remove(long goodsId) {
        getCart();
        mycart.remove(goodsId);
    }

    public void clear() {
        getCart();
        mycart.clear();
    }

    public String list() {
        getCart();
        return JSON.toJSONString(mycart.entrySet().toArray());
    }

    public String settle() {
        if (orderService.settleCart(getUserId())){
            clear();
            return "Cart Service Settled";
        }
        return "error";
    }

    public Long getUserId() {
        return (Long) session.getAttribute("userId");
    }

    public Map<Long, Goods> getCart() {
        mycart = (HashMap<Long, Goods>) session.getAttribute("mycart");
        if (mycart == null) {
            mycart = new HashMap<>();
            session.setAttribute("mycart", mycart);
        }
        return mycart;
    }

}
