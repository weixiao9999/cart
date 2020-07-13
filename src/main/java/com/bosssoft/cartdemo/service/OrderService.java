package com.bosssoft.cartdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bosssoft.cartdemo.dao.OrderItemMapper;
import com.bosssoft.cartdemo.dao.OrderMapper;
import com.bosssoft.cartdemo.entity.Goods;
import com.bosssoft.cartdemo.entity.Order;
import com.bosssoft.cartdemo.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author hujierong
 * @date 2020-7-10
 */
@Service
public class OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    GoodsService goodsService;

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper itemMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean settleCart (Long userId) {
        if (userId == null) {
            return false;
        }
        Order order = new Order();
        OrderItem item = null;
        Map<Long, Goods> cart = cartService.getCart();
        Iterator<Long> cartIt = cart.keySet().iterator();
        Goods goods = null;

        order.setUserId(userId);
        orderMapper.insert(order);

        while (cartIt.hasNext()) {
            goods = cart.get(cartIt.next());
            goods.setTotal(goods.getTotal() - goods.getNumber());
            goodsService.updateGoods(goods);

            item = new OrderItem();
            item.setGoodsId(goods.getGoodsId());
            item.setOrderId(order.getOrderId());
            item.setNumber(goods.getNumber());
            itemMapper.insert(item);
        }

        return true;
    }

    public List<Order> getOrderList() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getUserId, cartService.getUserId());
        return orderMapper.selectOrders(queryWrapper);
    }
}