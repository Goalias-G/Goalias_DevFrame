package com.aigc.vrmetal.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class GptUtil {
    public static String chat(String question){
        HttpRequest request = HttpUtil.createGet("http://211.64.41.151:9802/chat_gpt/"+question);
        HttpResponse response = request.execute();
        JSONObject result=new JSONObject(response.body());
        if (result.isEmpty()) return "请求失败";
        return result.getByPath("response").toString();
    }
    public static String transcribe(MultipartFile file){
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9803/transcribe");
        HttpResponse response = request.form("file",file)
                .bearerAuth("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")
                .contentType("multipart/form-data").execute();
        JSONObject result=new JSONObject(response.body());
        if (result.isEmpty()) return "请求失败";
        return result.getByPath("text").toString();
    }
}
