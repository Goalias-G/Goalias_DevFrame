package com.aigc.vrmetal.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

public class EmoUtil {
    //返回为-1表示请求失败
    public static Integer predict(MultipartFile audio_file){
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9804/predict-emotion");
        HttpResponse response = request.form("file",audio_file)
                .bearerAuth("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")
                .contentType("multipart/form-data").execute();
        JSONObject result=new JSONObject(response.body());
        if (result.isEmpty()) return -1;
        return (Integer) result.getByPath("emotion_index");
    }
    public static Integer multipartPredict(MultipartFile audio_file,MultipartFile video_file){
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9805/detect_emotion");
        HttpResponse response = request.form("audio_file",audio_file)
                .form("image_file",video_file)
                .bearerAuth("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")
                .contentType("multipart/form-data").execute();
        JSONObject result=new JSONObject(response.body());
        if (result.isEmpty()) return -1;
        return (Integer) result.getByPath("emotion_index");
    }
}
