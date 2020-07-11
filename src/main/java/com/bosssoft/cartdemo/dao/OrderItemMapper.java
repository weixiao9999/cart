package com.bosssoft.cartdemo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosssoft.cartdemo.entity.OrderItem;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-10
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
    @Select("SELECT order_item.item_id, order_item.goods_id, order_item.order_id, order_item.number, goods.name, goods.price"
            + " FROM order_item JOIN goods ON order_item.goods_id =goods.goods_id  WHERE order_id=#{orderId}")
    @MapKey("itemId")
    List<OrderItem> findItemsById(long orderId);
}
