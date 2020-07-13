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

    /**
     * 该方法用于往购物车中添加商品。
     * @param goods 商品
     * @return 添加是否成功
     */
    public boolean add(Goods goods) {
        getCart();
        mycart.put(goods.getGoodsId(), goods);
        return true;
    }

    /**
     * 该方法用于修改购物车中商品的数量。
     * @param goodsId 商品Id
     * @param number 商品数量
     * @return 修改是否成功
     */
    public boolean edit(long goodsId, int number) {
        getCart();
        Goods goods = mycart.get(goodsId);
        if (goods == null) {
            return false;
        }
        goods.setNumber(number);
        return true;
    }

    /**
     * 该方法用于移除购物车中的商品。
     * @param goodsId 商品Id
     */
    public void remove(long goodsId) {
        getCart();
        mycart.remove(goodsId);
    }

    /**
     * 该方法用于清除购物车。
     */
    public void clear() {
        getCart();
        mycart.clear();
    }


    /**
     * 该方法用于展示购物车中的商品。
     * @return 购物车map的字符串形式
     */
    public String list() {
        getCart();
        return JSON.toJSONString(mycart.entrySet().toArray());
    }

    /**
     * 该方法用于结算购物车。
     * @return 结算结果
     */
    public String settle() {
        if (orderService.settleCart(getUserId())){
            clear();
            return "Cart Service Settled";
        }
        return "error";
    }

    /**
     * 该方法用于获取session中的userId。
     * @return userId
     */
    public Long getUserId() {
        return (Long) session.getAttribute("userId");
    }

    /**
     * 该方法用于获取购物车。
     * @return 购物车
     */
    public Map<Long, Goods> getCart() {
        mycart = (HashMap<Long, Goods>) session.getAttribute("mycart");
        if (mycart == null) {
            mycart = new HashMap<>();
            session.setAttribute("mycart", mycart);
        }
        return mycart;
    }

}
