package com.bosssoft.cartdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosssoft.cartdemo.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}
