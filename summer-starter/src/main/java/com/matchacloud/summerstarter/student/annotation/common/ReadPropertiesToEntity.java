package com.matchacloud.summerstarter.student.annotation.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
/**
 * 将配置文件中的属性批量绑定到一个 Java Bean 上，适用于配置项较多的情况
 */
@ConfigurationProperties(prefix = "my")
public class ReadPropertiesToEntity {
    private String property;
}
