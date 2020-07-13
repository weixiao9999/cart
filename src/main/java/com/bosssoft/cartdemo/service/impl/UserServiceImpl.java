package com.bosssoft.cartdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bosssoft.cartdemo.dao.UserMapper;
import com.bosssoft.cartdemo.entity.User;
import com.bosssoft.cartdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @author hujierong
 * @date 2020-7-9
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Autowired
    private HttpSession session;

    @Override
    public boolean validateUser(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserName, userName);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return false;
        } else if (password.equals(user.getPassword())) {
            session.setAttribute("userId", user.getUserId());
            return true;
        }
        return false;
    }
}
