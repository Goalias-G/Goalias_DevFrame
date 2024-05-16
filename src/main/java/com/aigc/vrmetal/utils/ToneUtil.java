package com.aigc.vrmetal.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

public class ToneUtil {
    //2为发出请求失败，1为模型出错，0为请求成功
    public static Integer uvr5(String model_name,String input_root,String save_root_vocal,String save_root_ins,String agg){
        JSONObject json=new JSONObject();
        json.set("model_name",model_name);
        json.set("input_root",input_root);
        json.set("save_root_vocal",save_root_vocal);
        json.set("save_root_ins",save_root_ins);
        json.set("agg",agg);
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9800/uvr5");
        HttpResponse response = request.body(json.toString()).execute();
        JSONObject result=new JSONObject(response.body());
        if (result.isEmpty()) return 2;
        return (Integer) result.getByPath("code");
    }
    public static Integer slice(String inp,String opt_root){
        JSONObject json=new JSONObject();
        json.set("inp",inp);
        json.set("opt_root",opt_root);
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9800/slice");
        HttpResponse response = request.body(json.toString()).execute();
        JSONObject result=new JSONObject(response.body());
        if (result.isEmpty()) return 2;
        return (Integer) result.getByPath("code");
    }
    public static Integer denoise(String input_folder,String output_folder){
        JSONObject json=new JSONObject();
        json.set("input_folder",input_folder);
        json.set("output_folder",output_folder);
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9800/denoise");
        HttpResponse response = request.body(json.toString()).execute();
        JSONObject result=new JSONObject(response.body());
        if (result.isEmpty()) return 2;
        return (Integer) result.getByPath("code");
    }
    public static String asr(String input_folder,String output_folder){
        JSONObject json=new JSONObject();
        json.set("input_folder",input_folder);
        json.set("output_folder",output_folder);
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9800/asr");
        HttpResponse response = request.body(json.toString()).execute();
        JSONObject result=new JSONObject(response.body());
        if (result.getByPath("code").equals(0)|| result.isEmpty()) return "请求失败";
        return result.getByPath("info").toString();
    }
    public static Integer preprocess(String inp_text,String inp_wav_dir,String exp_name){
        JSONObject json=new JSONObject();
        json.set("inp_text",inp_text);
        json.set("inp_wav_dir",inp_wav_dir);
        json.set("exp_name",exp_name);
        HttpRequest request = HttpUtil.createPost("http://211.64.41.151:9800/preprocess");
        HttpResponse response = request.body(json.toString()).execute();
        JSONObject result=new JSONObject(response.body());
        if (result.isEmpty()) return 2;
        return (Integer) result.getByPath("code");
    }


}
