package com.dev.model.context.exception;

import lombok.Data;

@Data
public class BizException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    protected Integer errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    public BizException(ExceptionEnum exception) {
        this.errorCode = exception.getResultCode();
        this.errorMsg = exception.getResultMsg();
    }


    public BizException(String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = 550;
    }

    public BizException(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
