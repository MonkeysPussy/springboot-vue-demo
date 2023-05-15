package com.ruiwang.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruiwang.springboot.common.Result;
import com.ruiwang.springboot.entity.Travel;
import com.ruiwang.springboot.service.TravelService;
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
@RequestMapping("/travel")
public class TravelController {

    @Autowired
    TravelService travelService;


    @PostMapping
    public Result<?> saveTravel(@RequestBody Travel travel) {

        travelService.save(travel);
        return Result.success();
    }

    @PutMapping
    public Result<?> updateTravel(@RequestBody Travel travel) {
        travelService.updateById(travel);
        return Result.success();
    }

    @DeleteMapping("/{pid}")
    public Result<?> deleteTravel(@PathVariable Integer pid) {
        travelService.removeById(pid);
        return Result.success();
    }

    @GetMapping
    public Result<?> SelectTravelPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                      @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Travel> wrapper = Wrappers.<Travel>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            wrapper.like(Travel::getPName, search);
        }


        Page<Travel> TravelPage = travelService.page(new Page<>(pageNum, pageSize), wrapper);


        return Result.success(TravelPage);
    }


}
