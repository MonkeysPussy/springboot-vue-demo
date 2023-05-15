package com.ruiwang.springboot.controller;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruiwang.springboot.common.Result;
import com.ruiwang.springboot.entity.News;
import com.ruiwang.springboot.entity.Travel;
import com.ruiwang.springboot.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @BelongsProject: springboot-vue-demo
 * @BelongsPackage: com.ruiwang.springboot.config
 * @Author: ruiwang
 * @CreateTime: 2023-04-13 15:25
 * @Description:
 * @Version:1.0
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;


    @PostMapping
    public Result<?> saveNews(@RequestBody News news) {

        news.setTime(new Date());
        newsService.save(news);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateNews(@RequestBody News news) {
        newsService.updateById(news);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteNews(@PathVariable Integer id) {
        newsService.removeById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> SelectNewsPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            wrapper.like(News::getTitle, search);
        }


        Page<News> NewsPage = newsService.page(new Page<>(pageNum, pageSize), wrapper);


        return Result.success(NewsPage);
    }


}
