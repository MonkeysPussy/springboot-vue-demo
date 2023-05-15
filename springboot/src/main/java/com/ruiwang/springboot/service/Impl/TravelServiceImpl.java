package com.ruiwang.springboot.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruiwang.springboot.entity.Travel;
import com.ruiwang.springboot.entity.User;
import com.ruiwang.springboot.mapper.TravelMapper;
import com.ruiwang.springboot.mapper.UserMapper;
import com.ruiwang.springboot.service.TravelService;
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
public class TravelServiceImpl extends ServiceImpl<TravelMapper, Travel> implements TravelService {
}
