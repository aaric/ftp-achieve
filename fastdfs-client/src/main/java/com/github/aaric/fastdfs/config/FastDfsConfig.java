package com.github.aaric.fastdfs.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * FastDFSConfig
 *
 * @author Aaric, created on 2020-08-11T10:25.
 * @version 1.1.0-SNAPSHOT
 */
@Configuration
@Import(FdfsClientConfig.class)
public class FastDfsConfig {
}
