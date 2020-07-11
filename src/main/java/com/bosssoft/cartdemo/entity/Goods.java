package com.bosssoft.cartdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Data
public class Goods {
    @TableId(type = IdType.AUTO)
    private Long goodsId;

    private String name;

    private double price;

    private int total;

    @TableField(exist = false)
    private int number;
}
