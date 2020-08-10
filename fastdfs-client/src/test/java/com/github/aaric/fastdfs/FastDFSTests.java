package com.github.aaric.fastdfs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @Test
    public void testPrint() {
        log.info("hello fastdfs");
    }
}
