package com.ruiwang.springboot.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruiwang.springboot.entity.News;
import com.ruiwang.springboot.mapper.NewsMapper;
import com.ruiwang.springboot.service.NewsService;
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
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
}
