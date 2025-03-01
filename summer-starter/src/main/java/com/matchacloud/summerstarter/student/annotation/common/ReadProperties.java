package com.matchacloud.summerstarter.student.annotation.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读配置文件
 */
@Component
public class ReadProperties {
    /**
     * 从配置文件（如 application.properties 或 application.yml）中读取属性值，并注入到对应的字段中
     */
    @Value("${logging.file.name}")
    private String logFileName;
}
