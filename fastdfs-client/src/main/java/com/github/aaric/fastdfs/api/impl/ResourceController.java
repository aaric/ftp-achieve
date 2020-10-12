package com.github.aaric.fastdfs.api.impl;

import com.github.aaric.fastdfs.api.ResourceApi;
import com.wf.captcha.ArithmeticCaptcha;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    @GetMapping(value = "/uploadImage")
    public String uploadImage() {
        return "uploadImage";
    }

    @Override
    @GetMapping("/captcha")
//    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
    public Map<String, String> captcha() {
//        // GIF
//        GifCaptcha captcha = new GifCaptcha(130, 48);
//        //ChineseGifCaptcha captcha = new ChineseGifCaptcha(130, 48);
//        System.err.println(captcha.text());
//        CaptchaUtil.out(captcha, request, response);

        // Math
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
//        captcha.setLen(3);
//        System.err.println(captcha.getArithmeticString());
//        System.err.println(captcha.text());
//        CaptchaUtil.out(captcha, request, response);

        // JSON
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        captcha.setLen(3);
        System.err.println(captcha.getArithmeticString());
        System.err.println(captcha.text());
        String imageKey = UUID.randomUUID().toString();
        String imageBase64 = captcha.toBase64();

        Map<String, String> imageMap = new HashMap<>();
        imageMap.put("key", imageKey);
        imageMap.put("base64", imageBase64);
        return imageMap;
    }
}
