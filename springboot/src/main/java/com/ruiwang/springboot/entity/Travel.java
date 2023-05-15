package com.ruiwang.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @BelongsProject: springboot-vue-demo
 * @BelongsPackage: com.ruiwang.springboot.entity
 * @Author: ruiwang
 * @CreateTime: 2023-04-19 22:19
 * @Description:
 * @Version:1.0
 */
@TableName("travel")
@Data
public class Travel {
    @TableId(type = IdType.AUTO)
    private Integer pid;
    private String pName;
    private String pType;
    private String province;
    private String traffic;
    private String pAddress;
    private String details;
    private String img;
}
