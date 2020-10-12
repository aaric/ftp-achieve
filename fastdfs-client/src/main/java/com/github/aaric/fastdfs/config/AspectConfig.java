package com.github.aaric.fastdfs.config;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * AspectConfig
 *
 * @author Aaric, created on 2020-08-14T13:49.
 * @version 1.2.0-SNAPSHOT
 */
@Slf4j
@Aspect
@Configuration
@ConditionalOnProperty("spring.profiles.active")
public class AspectConfig {

    /**
     * 拦截控制器通用请求注解
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void apiRoute() {
    }

    /**
     * 拦截控制器Get请求注解
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void apiGetRoute() {
    }

    /**
     * 拦截控制器Post请求注解
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void apiPostRoute() {
    }

    /**
     * 拦截控制器Put请求注解
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void apiPutRoute() {
    }

    /**
     * 拦截控制器Delete请求注解
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void apiDeleteRoute() {
    }

    /**
     * 处理API请求计数
     */
    //@Before("apiRoute() || apiGetRoute() || apiPostRoute() || apiPutRoute() || apiDeleteRoute()")
    public void processApiCountBefore() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        log.info("url -> {}", request.getRequestURI());
    }

    /**
     * 处理API请求计数
     */
    @Around("apiRoute() || apiGetRoute() || apiPostRoute() || apiPutRoute() || apiDeleteRoute()")
    public Object processApiCountAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取Request对象
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        // 记录日志
        log.info("url -> {}", request.getRequestURI());

        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        System.err.println(apiOperation);

        // 执行方法
        Object result = joinPoint.proceed(joinPoint.getArgs());

        // 返回结果
        return result;
    }
}
