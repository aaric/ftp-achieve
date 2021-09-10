package com.github.aaric.vsftp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

/**
 * FtpServiceTest
 *
 * @author Aaric, created on 2018-12-10T21:30.
 * @version 0.1.0-SNAPSHOT
 */
@Disabled
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FtpServiceTest {

    /**
     * 测试文件
     */
    private static String testFileDirectory = "C:\\Users\\root\\Desktop\\";
    private static String testFileName = "404.jpg";

    @Autowired
    private FtpService ftpService;

    @Test
    public void testUploadFile() {
        ftpService.uploadFile("/404/" + testFileName, new File(testFileDirectory, testFileName));
    }
}
