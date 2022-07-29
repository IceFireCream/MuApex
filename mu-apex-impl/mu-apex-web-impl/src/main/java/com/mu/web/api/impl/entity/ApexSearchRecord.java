package com.mu.web.api.impl.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("apex_search_record")
public class ApexSearchRecord {
    @TableId
    private String userId;
    private String userName;
    private String rankScore;
    private String rankName;
    private String arenaScore;
    private String arenaName;
    private Date searchTime;
    private String rankPos;
    private String arenaPos;
    private String totalKills;
    private String totalDamage;
    private String level;
    private String rankImg;
    private String arenaImg;
    private String currentState;
    private String banner;
    private String selectedLegend;
}
