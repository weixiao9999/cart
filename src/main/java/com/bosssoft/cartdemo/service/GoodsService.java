package com.bosssoft.cartdemo.service;

import com.bosssoft.cartdemo.dao.GoodsMapper;
import com.bosssoft.cartdemo.entity.Goods;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Service
public class GoodsService {
    @Resource
    GoodsMapper goodsMapper;

    public void updateGoods(Goods goods) {
        goodsMapper.updateById(goods);
    }
}
