package com.bosssoft.cartdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author hujierong
 * @date 2020-7-10
 */
@Data
public class OrderItem {
    @TableId(type = IdType.AUTO)
    private Long itemId;

    private Long goodsId;

    private Long orderId;

    private int number;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private double price;
}
