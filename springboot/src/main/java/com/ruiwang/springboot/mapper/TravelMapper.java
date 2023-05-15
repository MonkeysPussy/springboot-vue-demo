package com.ruiwang.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruiwang.springboot.entity.Travel;
import com.ruiwang.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @BelongsProject: springboot-vue-demo
 * @BelongsPackage: com.ruiwang.springboot.mapper
 * @Author: ruiwang
 * @CreateTime: 2023-04-13 15:38
 * @Description:
 * @Version:1.0
 */
@Mapper
public interface TravelMapper extends BaseMapper<Travel> {
}
