package com.dev.model.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teautil.models.RuntimeOptions;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信相关的工具类*
 */
@Slf4j
@Data
public class SMSUtils {

    @Value("${goalias.aliyun.accessKeyId}")
    private static String accessKeyId ;

    @Value("${goalias.aliyun.accessKeySecret}")
    private static String accessKeySecret;

    private static String endpoint="dysmsapi.aliyuncs.com";


    /**
     * 检测手机号有效性*
     * @param mobile 手机号码
     * @return 是否有效
     */
    public static boolean isMobileNum(String mobile){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 生成短信验证码*
     * @param length 长度
     * @return 指定长度的随机短信验证码
     */
    public static String randomSMSCode(int length, boolean numberFlag) {
        String retStr;
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);
        return retStr;
    }

    /**
     * 发送短信验证码*
     * @param mobile 手机号码
     * @param code 验证码
     * @return 是否发送成功
     */
    public static boolean sendCode(String mobile, String code) {
        SendSmsRequest sendSmsRequest = new com.aliyun.dysmsapi20170525.models.SendSmsRequest()
                .setPhoneNumbers(mobile)
                .setSignName("dev_frame")//注册签名
                .setTemplateCode("SMS_********")//模板
                .setTemplateParam("{'code':" + code + "}");
        Client client = null;
        try {
            client = createClient();
        } catch (Exception e) {
            log.warn("创建短信客户端失败{}", ExceptionUtil.stacktraceToString(e));
        }
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = client.sendSmsWithOptions(sendSmsRequest, new RuntimeOptions());
        } catch (Exception e) {
            log.warn("发送短信失败{}", sendSmsResponse.getBody().getMessage());
        }

        return true;
    }
    public static com.aliyun.dysmsapi20170525.Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
        config.endpoint = endpoint;
        return new com.aliyun.dysmsapi20170525.Client(config);
    }
}