package com.bosssoft.cartdemo.service;

import com.bosssoft.cartdemo.dao.GoodsMapper;
import com.bosssoft.cartdemo.entity.Goods;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Service
public class GoodsService {
    @Resource
    GoodsMapper goodsMapper;

    /**
     * 该方法用于获取商品列表。
     * @return 商品列表
     */
    public List<Goods> getGoodsList() {
        return goodsMapper.selectList(null);
    }

    /**
     * 该方法用于修改数据库中的商品。
     * @param goods 商品
     */
    public void updateGoods(Goods goods) {
        goodsMapper.updateById(goods);
    }
}
