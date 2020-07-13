package com.bosssoft.cartdemo.service.impl;

import com.bosssoft.cartdemo.dao.GoodsMapper;
import com.bosssoft.cartdemo.entity.Goods;
import com.bosssoft.cartdemo.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    GoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.selectList(null);
    }


    @Override
    public void updateGoods(Goods goods) {
        goodsMapper.updateById(goods);
    }
}
