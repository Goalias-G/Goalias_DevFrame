package com.dev.model.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * request工具类
 *
*/
public class IPUtil {

    private static final String UNKNOWN = "unknown";

    public static String getIp(HttpServletRequest request){
        String realIp = request.getHeader("X-Real-IP");
        String forwardedIp = request.getHeader("X-Forwarded-IP");
        if (StringUtils.hasText(forwardedIp) && UNKNOWN.equalsIgnoreCase(forwardedIp)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = forwardedIp.indexOf(",");
            if(index != -1){
                return forwardedIp.substring(0,index);
            }else{
                return forwardedIp;
            }
        }
        forwardedIp = realIp;

        if(!StringUtils.hasText(forwardedIp) && !UNKNOWN.equalsIgnoreCase(forwardedIp)){
            return forwardedIp;
        }
        if (!StringUtils.hasText(forwardedIp) || UNKNOWN.equalsIgnoreCase(forwardedIp)) {
            forwardedIp = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasText(forwardedIp) || UNKNOWN.equalsIgnoreCase(forwardedIp)) {
            forwardedIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasText(forwardedIp) || UNKNOWN.equalsIgnoreCase(forwardedIp)) {
            forwardedIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasText(forwardedIp) || UNKNOWN.equalsIgnoreCase(forwardedIp)) {
            forwardedIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasText(forwardedIp) || UNKNOWN.equalsIgnoreCase(forwardedIp)) {
            forwardedIp = request.getRemoteAddr();
        }
        return forwardedIp;
    }

    public static String getAddressByIP(String ip) {
        if (!StringUtils.hasText(ip)){
            return UNKNOWN;
        }
        if (ip.equals("127.0.0.1")){
            return "本机";
        }
        HttpResponse httpResponse = HttpUtil.createGet("https://opendata.baidu.com/api.php")
                .form("query", ip)
                .form("oe", "utf-8")
                .form("resource_id", "6006").execute();
        JSONObject response = new JSONObject(httpResponse.body());
        if (response.get("data") != null){
            return response.getJSONArray("data").getJSONObject(0).getStr("location");
        }
        return UNKNOWN;
    }



}
