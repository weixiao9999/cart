package com.bosssoft.cartdemo.controller;

import com.bosssoft.cartdemo.dto.ResponseResult;
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
        public ResponseResult list() {
        return new ResponseResult(200, orderService.getOrderList());
    }
}
