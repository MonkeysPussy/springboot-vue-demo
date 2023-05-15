package com.ruiwang.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @BelongsProject: springboot-vue-demo
 * @BelongsPackage: com.ruiwang.springboot.entity
 * @Author: ruiwang
 * @CreateTime: 2023-04-13 15:31
 * @Description:
 * @Version:1.0
 */
@TableName("user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String nickName;
    private Integer age;
    private String sex;
    private String address;
    private String profilePhoto;
    private Integer role;
}
