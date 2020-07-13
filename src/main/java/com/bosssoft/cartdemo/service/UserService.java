package com.bosssoft.cartdemo.service;

/**
 * @author hujierong
 * @date 2020-7-13
 */
public interface UserService {

    /**
     * 该方法用于验证用户登录的用户名和密码是否一致。
     * @param userName 用户名
     * @param password 密码
     * @return 验证是否正确
     */
    boolean validateUser(String userName, String password);
}
