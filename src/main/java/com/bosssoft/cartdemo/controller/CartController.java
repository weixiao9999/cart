package com.bosssoft.cartdemo.controller;

import com.bosssoft.cartdemo.dto.ResponseResult;
import com.bosssoft.cartdemo.entity.Goods;
import com.bosssoft.cartdemo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseResult add(@RequestBody Goods goods) {
        cartService.add(goods);
        return new ResponseResult(200, "add goods: " + goods.getName());
    }

    @PostMapping("/edit")
    public ResponseResult edit(@RequestBody Goods goods) {
        if (cartService.edit(goods.getGoodsId(), goods.getNumber())) {
            return new ResponseResult(200, "edit goods: " + goods.getName() + " number to " + goods.getNumber());
        }
        return new ResponseResult(601, "edit error! ");
    }

    @PostMapping("/remove")
    public ResponseResult remove(@RequestBody Goods goods) {
        cartService.remove(goods.getGoodsId());
        return new ResponseResult(200, "remove goods: " + goods.getName());
    }

    @PostMapping("/clear")
    public ResponseResult remove() {
        cartService.clear();
        return new ResponseResult(200, "clear goods! ");
    }

    @PostMapping("/list")
    public ResponseResult list() {
        return new ResponseResult(200, cartService.getCart());
    }

    @PostMapping("/settle")
    public ResponseResult settle() {
        if (cartService.settle()) {
            return new ResponseResult(200, "Cart Service Settled");
        }
        return new ResponseResult(602, "settle error! ");
    }
}
