package com.dev.model.aop;


import cn.hutool.json.JSONUtil;
import com.dev.model.context.BizException;
import com.dev.model.pojo.vo.Result;
import com.dev.model.utils.RequestUtil;
import com.google.common.base.Stopwatch;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * controller所有请求日志记录
 */
@Slf4j
@Aspect
@Configuration
@ConditionalOnProperty(prefix = "goalias.log", name = "isOpen", havingValue = "true")
public class RequestLogAspect {

    private static final Logger Goalias_LOGGER = LoggerFactory.getLogger("Goalias");

    @Pointcut("execution(public com.dev.model.pojo.vo.Result com.dev.model.controller..*Controller.*(..))")
    public void controllerLogAspect() {
    }

    @Around(value = "controllerLogAspect()")
    public Result beforeRequest(ProceedingJoinPoint point) throws Throwable {
        StringBuilder logAfterStr = new StringBuilder();
        Result result;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        String token = request.getHeader("Token");
        String method = request.getMethod();
        String userAgent = request.getHeader("user-agent");
        String xRequestId = request.getHeader("x-request-id");
        String ip = RequestUtil.getIp(request);
        String methodName = point.getSignature().getName();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String requestURI = request.getRequestURI();
        Boolean isSuccess = true;
        logAfterStr.append("\n");
        logAfterStr.append("#####################Request#####################").append("\n");
        logAfterStr.append("Timestamp    :").append(timestamp).append("\n");
        logAfterStr.append("userAgent    :").append(userAgent).append("\n");
        logAfterStr.append("MethodName   :").append(methodName).append("\n");
        logAfterStr.append("UserToken    :").append(token).append("\n");
        logAfterStr.append("RequestIP    :").append(ip).append("\n");
        logAfterStr.append("RequestURI   :").append(requestURI).append("\n");
        logAfterStr.append("x-request-id :").append(xRequestId).append("\n");
        logAfterStr.append("RequestParams:");
        if (point.getArgs() != null && point.getArgs().length > 0) {
            Arrays.stream(point.getArgs()).
                filter(o -> !(o instanceof BindingResult)).
                forEach(o -> logAfterStr.append(JSONUtil.toJsonStr(o)));
            logAfterStr.append("\n");
        }
        log.info(logAfterStr.toString());
        Stopwatch stopwatch = Stopwatch.createStarted();
        Throwable errorThrow = null;
        try {
            StringBuilder logResponseStr = new StringBuilder();
            result = (Result) point.proceed();
            logResponseStr.append("\n");
            logResponseStr.append("#####################Response#####################").append("\n");
            logResponseStr.append("Timestamp    :").append(timestamp).append("\n");
            logResponseStr.append("MethodName   :").append(methodName).append("\n");
            logResponseStr.append("UserToken    :").append(token).append("\n");
            logResponseStr.append("RequestIP    :").append(ip).append("\n");
            logResponseStr.append("RequestURI   :").append(requestURI).append("\n");
            logResponseStr.append("RunTime      :").append(stopwatch.elapsed(TimeUnit.MILLISECONDS)).append("ms\n");
            logResponseStr.append("Result   :").append(result).append("\n");;
            logResponseStr.append("x-request-id :").append(xRequestId);
            log.info(logResponseStr.toString());
        } catch (Throwable e) {
            errorThrow = e;
            isSuccess = false;
//            // 如果是自定义的业务异常, 继续抛出让ExceptionControllerHandler去捕获, 这样可以回显前端详细的错误信息, 而不是简单的"操作失败"
            if (e instanceof BizException || e instanceof NullPointerException) {
                throw e;
            }
            String logErrorStr = "\n" +
                "#####################系统全局异常监控Exception#####################" + "\n" +
                "Timestamp    :" + timestamp + "\n" +
                "MethodName   :" + methodName + "\n" +
                "UserToken    :" + token + "\n" +
                "RequestIP    :" + ip + "\n" +
                "RequestURI   :" + requestURI + "\n" +
                "x-request-id       :" + xRequestId + "\n" +
                "Exception    :" + e.getMessage() + "\n";
            log.error(logErrorStr);
            log.error("ExceptionStackTrace   :", e);
            return Result.error("common.fail");
        } finally {
            MDC.put("runTime", String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS)));
            MDC.put("url", requestURI);
            MDC.put("userAgent", userAgent);
            MDC.put("requestType", method);
            MDC.put("errorCode", isSuccess ? "0" : "1");
            MDC.put("errorMsg", errorThrow == null ? "" : errorThrow.getMessage());
            MDC.put("remoteAddr", request.getRemoteAddr());
            MDC.put("x-request-id", xRequestId);
            Goalias_LOGGER.info(" ");
        }
        return result;
    }

}
