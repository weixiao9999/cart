package com.bosssoft.cartdemo.service;

import com.bosssoft.cartdemo.entity.Goods;

import java.util.HashMap;

/**
 * @author hujierong
 * @date 2020-7-13
 */
public interface CartService {

    /**
     * 该方法用于往购物车中添加商品。
     * @param goods 商品
     */
    void add(Goods goods);

    /**
     * 该方法用于修改购物车中商品的数量。
     * @param goodsId 商品Id
     * @param number 商品数量
     * @return 修改是否成功
     */
    boolean edit(long goodsId, int number);

    /**
     * 该方法用于移除购物车中的商品。
     * @param goodsId 商品Id
     */
    void remove(long goodsId);

    /**
     * 该方法用于清除购物车。
     */
    void clear();

    /**
     * 该方法用于展示购物车中的商品。
     * @return 购物车map的字符串形式
     */
    String list();

    /**
     * 该方法用于结算购物车。
     * @return 结算结果
     */
    String settle();

    /**
     * 该方法用于获取session中的userId。
     * @return userId
     */
    Long getUserId();

    /**
     * 该方法用于获取购物车。
     * @return 购物车
     */
    HashMap<Long, Goods> getCart();
}
