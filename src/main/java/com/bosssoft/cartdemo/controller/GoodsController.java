package com.bosssoft.cartdemo.controller;

import com.bosssoft.cartdemo.dto.ResponseResult;
import com.bosssoft.cartdemo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hujierong
 * @date 2020-7-13
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @PostMapping("/list")
    public ResponseResult list() {
        return new ResponseResult(200, goodsService.getGoodsList());
    }
}
