package com.bosssoft.cartdemo.controller;

import com.bosssoft.cartdemo.dto.ResponseResult;
import com.bosssoft.cartdemo.entity.User;
import com.bosssoft.cartdemo.service.UserService;
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
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user) {
        if (userService.validateUser(user.getUserName(), user.getPassword())) {
            return new ResponseResult(200, "Login successful");
        }
        return new ResponseResult(600, "Wrong user name or password");
    }
}
