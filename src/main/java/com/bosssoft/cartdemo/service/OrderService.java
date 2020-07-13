package com.bosssoft.cartdemo.service;

import com.bosssoft.cartdemo.entity.Order;

import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-13
 */
public interface OrderService {

    /**
     * 该方法用于结算购物车。
     * @param userId 用户Id
     * @return 结算是否成功
     */
    boolean settleCart (Long userId);

    /**
     * 该方法用于获取订单列表。
     * @return 订单列表
     */
    List<Order> getOrderList();
}
