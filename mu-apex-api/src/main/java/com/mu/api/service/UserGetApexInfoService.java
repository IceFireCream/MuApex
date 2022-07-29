package com.mu.api.service;

import com.alibaba.fastjson.JSONObject;
import com.mu.api.entity.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserGetApexInfoService {
    @GetMapping("getRankInfo")
    BaseResponse<JSONObject> getRankInfo(String player, String platform);
}
