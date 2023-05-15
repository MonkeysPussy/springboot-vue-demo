package com.ruiwang.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruiwang.springboot.common.Result;
import com.ruiwang.springboot.entity.User;
import com.ruiwang.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @BelongsProject: springboot-vue-demo
 * @BelongsPackage: com.ruiwang.springboot.config
 * @Author: ruiwang
 * @CreateTime: 2023-04-13 15:25
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result<?> loginUser(@RequestBody User user) {
        User res = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        System.out.println(user);
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> registerUser(@RequestBody User user) {
        User res = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if (res != null) {
            return Result.error("-1", "用户重复！！！");
        }
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }

        userService.save(user);
        System.out.println(user);
        return Result.success();
    }


    @PostMapping
    public Result<?> saveUser(@RequestBody User user) {
        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        userService.save(user);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> updateUser(@PathVariable Integer id) {
        userService.removeById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> SelectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            wrapper.like(User::getNickName, search);
        }


        Page<User> userPage = userService.page(new Page<>(pageNum, pageSize), wrapper);


        return Result.success(userPage);
    }

    @GetMapping("/{id}")
    public Result<?> selectOneUser(@PathVariable Integer id) {
        return Result.success(userService.getById(id));
    }

//    @GetMapping("/select")
//    public User SeleclUser(Integer id) {
//        return userService.getById(id);
//    }
}
