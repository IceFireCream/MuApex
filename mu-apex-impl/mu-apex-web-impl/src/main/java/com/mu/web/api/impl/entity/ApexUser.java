package com.mu.web.api.impl.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("apex_user")
public class ApexUser {

    @TableId
    private String userId;

    private String userName;

    private String platform;
    @TableLogic
    private int isExpired;
}
