package com.mu.web.api.impl.service;

import com.alibaba.fastjson.JSONObject;
import com.mu.api.entity.BaseResponse;
import com.mu.api.service.UserGetApexInfoService;
import com.mu.api.utils.BaseApiUtils;
import com.mu.web.api.impl.utils.HttpsClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RefreshScope
public class UserGetApexInfoServiceImpl extends BaseApiUtils<JSONObject> implements UserGetApexInfoService {

    @Value("${apex.url}")
    private String apexUrl;

    @Autowired
    private HttpsClientUtils httpsClientUtils;

    @Override
    @ResponseBody
    public BaseResponse<JSONObject> getRankInfo(String player, String platform) {
        //判断键入值是否合法
        if (ObjectUtils.isEmpty(player)) {
            return setResultError("请正确输入用户名");
        }
        if (ObjectUtils.isEmpty(platform)) {
            //默认平台为PC，尽管前端不可能为空，但是这里再次验证一遍
            platform = "PC";
        }
        String replaceUrl = apexUrl.replace("{player}", player);
        String realUrl = replaceUrl.replace("{platform}", platform);

        //调用url获取数据
        String httpsGet = httpsClientUtils.httpsGet(realUrl);
        if (httpsGet == null){
            return setResultError("未查询到数据，请确保用户名跟平台正确");
        }

        JSONObject body = JSONObject.parseObject(httpsGet);
        if (body == null) {
            return setResultError(null);
        }

        //拿到global.realtime
        JSONObject global = body.getJSONObject("global");
        JSONObject rank = null;
        JSONObject arena = null;
        String level = "";
        String uid = "";
        if (global != null){
            rank = global.getJSONObject("rank");
            arena = global.getJSONObject("arena");
            level = global.getString("level");
            uid = global.getString("uid");
        }

        JSONObject realtime = body.getJSONObject("realtime");
        String selectedLegend = "";
        String currentState = "";
        if (realtime != null){
            selectedLegend = realtime.getString("selectedLegend");
            currentState = realtime.getString("currentState");
        }
        JSONObject legends = body.getJSONObject("legends");
        JSONObject selected = null;
        if (legends != null){
            selected = legends.getJSONObject("selected");

        }
        JSONObject imgAssets = null;
        if (selected != null){
            imgAssets = selected.getJSONObject("ImgAssets");
        }
        String banner = "";
        if (imgAssets != null){
            banner = imgAssets.getString("banner");
        }
        JSONObject total = body.getJSONObject("total");
        JSONObject kills = null;
        JSONObject damage = null;
        if (total != null){
            kills = total.getJSONObject("kills");
            damage = total.getJSONObject("damage");
        }
        String totalKills = "";
        if (kills != null){
            totalKills = kills.getString("value");
        }
        String totalDamage = "";
        if (damage != null){
            totalDamage = damage.getString("value");
        }
        String rankName = "";
        String rankScore = "";
        String rankImg = "";
        String rankPos = "";
        if (rank != null){
            rankName = rank.getString("rankName");
            rankScore = rank.getString("rankScore");
            rankImg = rank.getString("rankImg");
            rankPos = rank.getString("ladderPosPlatform");
        }
        String arenaName = "";
        String arenaScore = "";
        String arenaImg = "";
        String arenaPos = "";
        if (arena != null){
            arenaName = arena.getString("rankName");
            arenaScore = arena.getString("rankScore");
            arenaImg = arena.getString("rankImg");
            arenaPos = arena.getString("ladderPosPlatform");
        }

        JSONObject response = new JSONObject();
        response.put("userId",uid);
        response.put("player",player);
        response.put("rankScore",rankScore);
        response.put("rankName",rankName);
        response.put("rankImg",rankImg);
        response.put("arenaScore",arenaScore);
        response.put("arenaName",arenaName);
        response.put("arenaImg",arenaImg);
        response.put("selectedLegend",selectedLegend);
        response.put("banner",banner);
        response.put("searchTime",new Date());
        response.put("rankPos",rankPos);
        response.put("arenaPos",arenaPos);
        response.put("totalKills",totalKills);
        response.put("totalDamage",totalDamage);
        response.put("currentState",currentState);
        response.put("level",level);

        return setResultSuccess(response);
    }

    /**
     * 转到主页
     * @return
     */
    @GetMapping("/")
    public String setIndex(){
        return "apexInfo";
    }
}
