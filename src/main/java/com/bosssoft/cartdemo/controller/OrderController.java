package com.bosssoft.cartdemo.controller;

import com.alibaba.fastjson.JSON;
import com.bosssoft.cartdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String list() {

        return JSON.toJSONString(orderService.getOrderList());
    }
}
