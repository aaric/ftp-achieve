package com.github.aaric.fastdfs.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 资源API接口
 *
 * @author Aaric, created on 2020-08-13T16:24.
 * @version 1.2.0-SNAPSHOT
 */
@Api(tags = "资源模块API")
public interface ResourceApi {

    @ApiOperation("测试文件上传")
    String uploadImage();

    @ApiOperation("测试文件上传")
    void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
