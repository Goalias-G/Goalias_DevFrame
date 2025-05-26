package com.dev.model.config;


import com.dev.model.context.exception.BizException;
import com.dev.model.context.exception.ExceptionEnum;
import com.dev.model.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class WebException {

    Logger logger = LoggerFactory.getLogger(WebException.class);

    @ExceptionHandler(value = BizException.class)
    public Result bizExceptionHandler(BizException e){
        logger.error("发生业务异常！原因是:{}",e.getErrorMsg());
        e.printStackTrace();
        return Result.error(e.getErrorCode(),e.getErrorMsg());
    }

    @ExceptionHandler(value =NullPointerException.class)
    public Result exceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:{}",e.getMessage());
        e.printStackTrace();
        return Result.error(ExceptionEnum.OBJECT_NULL_POINTER);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        logger.error("参数校验失败！原因是:{}", e.getMessage());
        e.printStackTrace();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        return Result.error(allErrors.stream().map(ObjectError::getDefaultMessage).reduce((a, b) -> a + ";" + b).get());
    }
    @ExceptionHandler(value = BindException.class)
    public Result bindExceptionHandler(BindException e) {
        logger.error("参数校验失败！原因是:{}", e.getMessage());
        e.printStackTrace();
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        return Result.error(allErrors.stream().map(ObjectError::getDefaultMessage).reduce((a, b) -> a + ";" + b).get());
    }


}
