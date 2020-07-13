package com.bosssoft.cartdemo.controller;

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
    public String add(@RequestBody Goods goods) {
        if (cartService.add(goods)) {
            return "add goods: " + goods.getName();
        }
        return "add error";
    }

    @PostMapping("/edit")
    public String edit(@RequestBody Goods goods) {
        if (cartService.edit(goods.getGoodsId(), goods.getNumber())) {
            return "edit goods: " + goods.getName() + " number to " + goods.getNumber();
        }
        return "edit error! ";
    }

    @PostMapping("/remove")
    public String remove(@RequestBody Goods goods) {
        cartService.remove(goods.getGoodsId());
        return "remove goods: " + goods.getName();
    }

    @PostMapping("/clear")
    public String remove() {
        cartService.clear();
        return "clear goods! ";
    }

    @PostMapping("/list")
    public String list() {
        return cartService.list();
    }

    @PostMapping("/settle")
    public String settle() {
        return cartService.settle();
    }
}
