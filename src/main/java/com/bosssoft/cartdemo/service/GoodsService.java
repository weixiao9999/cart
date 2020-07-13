package com.bosssoft.cartdemo.service;

import com.bosssoft.cartdemo.entity.Goods;

import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-13
 */
public interface GoodsService {

    /**
     * 该方法用于获取商品列表。
     * @return 商品列表
     */
    List<Goods> getGoodsList();

    /**
     * 该方法用于修改数据库中的商品。
     * @param goods 商品
     */
    void updateGoods(Goods goods);
}
