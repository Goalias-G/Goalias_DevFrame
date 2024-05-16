package com.aigc.vrmetal.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

public class SummaryUtil {
    public static String summary(String text){
        JSONObject json=new JSONObject();
        json.set("text",text);
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9801/dialogue/summary");
        HttpResponse response = request.body(json.toString()).execute();
        JSONObject result=new JSONObject(response.body());
        if (result.getByPath("code").equals(0)|| result.isEmpty()) return "请求失败";
        return result.getByPath("dialogue_summary").toString();
    }

}
