package com.matchacloud.summerstarter.student.mapper;

import com.matchacloud.summerstarter.student.domain.entity.User;
import org.springframework.stereotype.Component;

/**
 * 模拟db操作
 */
@Component
public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int updateByPrimaryKey(User record);

    User selectByPrimaryKey(Long id);
}
