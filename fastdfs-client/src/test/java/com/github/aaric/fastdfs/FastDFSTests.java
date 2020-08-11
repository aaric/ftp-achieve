package com.github.aaric.fastdfs;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Instant;

/**
 * FastDFSTests
 *
 * @author Aaric, created on 2020-08-10T10:29.
 * @version 1.0.0-SNAPSHOT
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FastDFSTests {

    @Value("${fdfs.test.base-path}")
    public String basePath;

    @Autowired
    private FastFileStorageClient storageClient;

    @Test
    public void testUpload() throws Exception {
        File uploadFile = new File(basePath + "/测试中文.txt");
        StorePath storePath = storageClient.uploadFile(new FileInputStream(uploadFile), uploadFile.length(), "txt", null);
        log.info("group: {}, path: {}, fullPath: {}", storePath.getGroup(), storePath.getPath(), storePath.getFullPath());
        Assertions.assertNotNull(storePath);
    }

    @Test
    public void testUploadImage() throws Exception {
        File uploadFile = new File(basePath + "/banzhuan.jpg");
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(new FileInputStream(uploadFile), uploadFile.length(), "jpg", null);
        log.info("group: {}, path: {}, fullPath: {}", storePath.getGroup(), storePath.getPath(), storePath.getFullPath());
        Assertions.assertNotNull(storePath);
    }

    @Test
    public void testDownload() throws Exception {
        String fullPath = "group1/M00/00/00/wKg4yF8yBjeAKD4tAAAAKAlQt5s888.txt";
        String group = fullPath.substring(0, fullPath.indexOf("/"));
        String path = fullPath.substring(fullPath.indexOf("/") + 1);
        byte[] fileBytes = storageClient.downloadFile(group, path, new DownloadByteArray());
        String testFilePath = basePath + "/测试中文.txt";
        File downloadFile = new File(testFilePath.substring(0, testFilePath.lastIndexOf("."))
                + Instant.now().getEpochSecond()
                + testFilePath.substring(testFilePath.lastIndexOf(".")));
        IOUtils.write(fileBytes, new FileOutputStream(downloadFile));
    }

    @Test
    public void testDelete() throws Exception {
        String fullPath = "group1/M00/00/00/wKg4yF8yBhiAKZ6DAAAAKAlQt5s645.txt";
        String group = fullPath.substring(0, fullPath.indexOf("/"));
        String path = fullPath.substring(fullPath.indexOf("/") + 1);
        storageClient.deleteFile(group, path);
    }
}
