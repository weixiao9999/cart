package com.bosssoft.cartdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @author hujierong
 * @date 2020-7-10
 */
@Data
@TableName("order_info")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long orderId;

    private Long userId;

    private String sn;

    @TableField(exist = false)
    private List<OrderItem> items;
}
