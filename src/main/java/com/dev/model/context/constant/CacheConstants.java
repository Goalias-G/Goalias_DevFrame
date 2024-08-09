package com.dev.model.context.constant;

/**
 * 缓存的key 常量
 */
public class CacheConstants {
    /**
     * 登录用户 redis token key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 登录用户 redis userId key
     */
    public static final String LOGIN_USER_KEY = "login_user:";
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
}
