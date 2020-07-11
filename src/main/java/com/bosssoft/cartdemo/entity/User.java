package com.bosssoft.cartdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Long userId;

    private String userName;

    private String password;
}
