package com.github.aaric.vsftp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 资源模块API
 *
 * @author Aaric, created on 2018-12-10T21:49.
 * @version 0.2.0-SNAPSHOT
 */
@Api(tags = "资源模块API")
public interface ResourceApi {

    @ApiOperation("上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uploadFile", value = "上传文件", dataType = "file", paramType = "form", required = true)
    })
    String uploadImage(MultipartFile uploadFile) throws IOException;
}
