package com.ruiwang.springboot.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruiwang.springboot.entity.User;
import com.ruiwang.springboot.mapper.UserMapper;
import com.ruiwang.springboot.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @BelongsProject: springboot-vue-demo
 * @BelongsPackage: com.ruiwang.springboot.service.Impl
 * @Author: ruiwang
 * @CreateTime: 2023-04-13 15:44
 * @Description:
 * @Version:1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
