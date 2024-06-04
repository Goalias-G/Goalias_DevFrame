package com.dev.model.config;


import com.dev.model.pojo.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class WebException {

    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e){
        log.error(e.toString(), e);
        return Result.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),"报错啦");
    }
}
