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
import org.springframework.transaction.interceptor.TransactionAspectSupport;

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

    /**
     * 该方法用于结算购物车。
     * <p>首先对用户Id进行判断，如果不存在，返回false。然后插入订单，并遍历购物车。
     * 最后将购物车中的商品插入到OrderItem表中，每次插入时对库存和购买数量进行比较，如果购买数量大于库存，回滚所有事务，返回false。</p>
     * @param userId 用户Id
     * @return 结算是否成功
     */
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
            if (goods.getNumber() > goods.getTotal()) {
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

    /**
     * 该方法用于获取订单列表。
     * @return 订单列表
     */
    public List<Order> getOrderList() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getUserId, cartService.getUserId());
        return orderMapper.selectOrders(queryWrapper);
    }
}
