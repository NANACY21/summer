package com.matchacloud.summerstarter.student.annotation.common;

import com.matchacloud.summerstarter.student.domain.dto.StudentDTO;
import com.matchacloud.summerstarter.student.domain.entity.User;
import com.matchacloud.summerstarter.student.domain.vo.StudentVO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 条件注解演示
 */
@Configuration
public class MyConditionalConfig {

    @Bean
    /**
     * 根据配置文件中的属性值来决定是否加载某个 Bean
     */
    @ConditionalOnProperty(name = "my.feature.enabled", havingValue = "true")
    public StudentVO myFeature() {
        return new StudentVO();
    }

    @Bean
    /**
     * @ConditionalOnClass 表示只有当类路径下存在指定的类时，才会加载对应的 Bean
     */
    @ConditionalOnClass
    public StudentDTO getStudentDTO(StudentVO studentVO) {
        return new StudentDTO();
    }

    @Bean
    /**
     * @ConditionalOnMissingClass 表示只有当类路径下不存在指定的类时，才会加载对应的 Bean
     */
    @ConditionalOnMissingClass
    public User getUser() {
        return new User();
    }
}
