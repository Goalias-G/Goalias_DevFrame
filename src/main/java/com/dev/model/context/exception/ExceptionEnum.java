package com.dev.model.context.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionEnum {

    // 数据操作错误定义
    SUCCESS(200, "操作成功"),
    NEED_LOGIN(401, "需要登录后操作"),
    NO_OPERATOR_AUTH(403, "无权限操作"),
    FROM_NOT_EXIST(405, "请求的资源库不存在"),
    RESOURCE_NOT_EXIST(406, "请求资源不存在"),
    SYSTEM_ERROR(500, "出现错误"),
    USERNAME_EXIST(501, "用户名已存在"),
    PHONENUMBER_EXIST(502, "手机号已存在"),
    EMAIL_ERROR(503, "邮箱相关错误"),
    LOGIN_ERROR(504, "用户名或密码错误"),
    REQUIRE_USER_INFO(505, "用户信息不能为空"),
    PARAM_NOT_VALID(506, "请求参数非法"),
    DATE_NOT_VALID(507, "日期格式非法"),
    OBJECT_NULL_POINTER(508, "空指针异常"),

    DATABASE_ERROR(509, "数据库操作异常");




    /**
     * 错误码
     */
    private final Integer resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    public Integer getResultCode() {
        return resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }
}
