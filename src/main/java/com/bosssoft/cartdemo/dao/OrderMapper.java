package com.bosssoft.cartdemo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bosssoft.cartdemo.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-10
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    @Results(id="orderMap", value = {
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "items", column = "order_id",one = @One(select = "com.bosssoft.cartdemo.dao.OrderItemMapper.findItemsById"))
    })
    @Select("SELECT * FROM order_info WHERE order_id=#{orderId}")
    Order findOrderById(long orderId);

    @Select("select * from order_info where 1=1 and " +
            "${ew.sqlSegment}")
    @ResultMap(value = "orderMap")
    List<Order> selectOrders(@Param("ew") QueryWrapper<Order> wrapper);
}
