package com.dev.model.config;


import com.dev.model.context.BizException;
import com.dev.model.pojo.vo.Result;
import com.dev.model.properties.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class WebException {

    Logger logger = LoggerFactory.getLogger(WebException.class);

    @ExceptionHandler(value = BizException.class)
    public Result bizExceptionHandler(BizException e){
        logger.error("发生业务异常！原因是:{}",e.getErrorMsg());
        return Result.error(e.getErrorCode(),e.getErrorMsg());
    }

    @ExceptionHandler(value =NullPointerException.class)
    public Result exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:{}",e.getMessage());
        return Result.error(ExceptionEnum.BODY_NOT_MATCH);
    }


}
