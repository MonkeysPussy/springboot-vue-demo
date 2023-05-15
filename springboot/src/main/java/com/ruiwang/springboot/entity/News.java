package com.ruiwang.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @BelongsProject: springboot-vue-demo
 * @BelongsPackage: com.ruiwang.springboot.entity
 * @Author: ruiwang
 * @CreateTime: 2023-04-19 22:19
 * @Description:
 * @Version:1.0
 */
@TableName("news")
@Data
public class News {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;

}
