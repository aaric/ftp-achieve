package com.github.aaric.fastdfs.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * AspectConfig
 *
 * @author Aaric, created on 2020-08-14T13:49.
 * @version 1.2.0-SNAPSHOT
 */
@Aspect
//@Configuration
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
     * 处理API请求次数统计
     */
    @Before("apiRoute() || apiGetRoute() || apiPostRoute() || apiPutRoute() || apiDeleteRoute()")
    public void processApiCount() {
        System.err.println("hello");
    }
}
