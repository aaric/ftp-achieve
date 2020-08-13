package com.github.aaric.fastdfs.api.impl;

import com.github.aaric.fastdfs.api.ResourceApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源API接口实现
 *
 * @author Aaric, created on 2020-08-13T16:26.
 * @version 1.2.0-SNAPSHOT
 */
@RestController
@RequestMapping("/api/resource")
public class ResourceController implements ResourceApi {

    @Override
    @RequestMapping(value = "/uploadImage", method = RequestMethod.GET)
    public String uploadImage() {
        return "uploadImage";
    }
}
