package cn.jxh.learning.swagger.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * cn.jxh.learning.swagger.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfter(JoinPoint joinPoint, Object ret) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        ApiOperation annotation = null;
        MethodSignature sign = (MethodSignature) joinPoint.getSignature();
        Method method = sign.getMethod();
        annotation = method.getAnnotation(ApiOperation.class);
        String apiId = joinPoint.getSignature().getName();
        if (annotation != null) {
            apiId = annotation.notes();
        }
        long excuteTime = System.currentTimeMillis() - startTime.get();
        Object[] args = joinPoint.getArgs();
        List<Object> arguments = new ArrayList<Object>();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile || args[i] == null) {
                continue;
            }
            arguments.add(args[i]);
        }
        log.info("{} {} {} 実行時間： {} ms 引数:{}", apiId, request.getMethod(), request.getRequestURL().toString(), excuteTime, JSONObject.toJSONString(arguments));

    }

}