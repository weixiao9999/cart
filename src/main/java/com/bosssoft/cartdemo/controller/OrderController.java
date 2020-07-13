package com.bosssoft.cartdemo.controller;

import com.bosssoft.cartdemo.entity.Order;
import com.bosssoft.cartdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-11
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/list")
        public List<Order> list() {
        return orderService.getOrderList();
    }
}
