package com.bosssoft.cartdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bosssoft.cartdemo.dao.OrderItemMapper;
import com.bosssoft.cartdemo.dao.OrderMapper;
import com.bosssoft.cartdemo.entity.Goods;
import com.bosssoft.cartdemo.entity.Order;
import com.bosssoft.cartdemo.entity.OrderItem;
import com.bosssoft.cartdemo.service.CartService;
import com.bosssoft.cartdemo.service.GoodsService;
import com.bosssoft.cartdemo.service.OrderService;
import com.bosssoft.cartdemo.util.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-10
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    CartService cartService;

    @Autowired
    GoodsService goodsService;

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper itemMapper;

    private IdWorker idWorker = new IdWorker(1, 1, 0);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean settleCart (Long userId) {
        if (userId == null) {
            log.info("用户未登录");
            return false;
        }
        Order order = new Order();
        OrderItem item = null;
        HashMap<Long, Goods> cart = cartService.getCart();

        //判断购物车是否为空
        if (cart.size() == 0) {
            log.info("购物车为空");
            return false;
        }

        Iterator<Long> cartIt = cart.keySet().iterator();
        Goods goods = null;

        //插入订单
        order.setUserId(userId);
        order.setSn(String.valueOf(idWorker.nextId()));
        orderMapper.insert(order);

        //插入OrderItem,修改商品库存
        while (cartIt.hasNext()) {
            goods = cart.get(cartIt.next());
            if (goods.getNumber() > goods.getTotal()) {
                log.debug("库存不足");
                //回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
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

    @Override
    public List<Order> getOrderList() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getUserId, cartService.getUserId());
        return orderMapper.selectOrders(queryWrapper);
    }
}
