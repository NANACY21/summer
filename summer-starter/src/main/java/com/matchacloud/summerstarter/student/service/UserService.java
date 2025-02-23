package com.matchacloud.summerstarter.student.service;

import com.matchacloud.summerstarter.student.domain.entity.User;
import com.matchacloud.summerstarter.student.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务注解:
 * 方法内的所有数据库操作都会在同一个事务中执行
 * 指定事务传播行为、事务隔离级别
 * <p>
 * 事务传播行为：
 * 1.事务传播行为主要是针对一个事务方法调用另一个事务方法的场景所采取的处理措施，但并不局限于此
 * 2.关注事务传播行为使用场景
 * <p>
 * springboot事务管理器:
 * 1.Spring Boot 事务管理器是 Spring Boot 框架中用于管理数据库事务的核心组件
 * 2.作用：1.进行database事务操作。2.资源管理：负责管理数据库连接等资源，在事务开始时获取连接，在事务结束时释放连接，确保资源的正确使用和回收
 * 3.主要实现类：DataSourceTransactionManager
 * 4.使用方式：1.@Transactional注解。2.编程式事务管理：通过在代码中手动获取事务管理器，手动开始事务、提交事务或回滚事务
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 默认，如果当前存在事务，则加入该事务(db操作加入到已有事务中 不再新建事务)；如果当前没有事务，则创建一个新的事务。
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void required() {
        User user = new User();
        int insert = userMapper.insert(user);
        if (insert != 1) {
            throw new RuntimeException("save fail");
        }
    }

    /**
     * 无论当前是否存在事务，都会创建一个新的事务。如果当前存在事务，则将当前事务挂起 当前事务会暂停执行 新事务ok后 恢复执行
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNew() {

    }

    /**
     * 如果当前存在事务，则在嵌套事务内执行；如果当前没有事务，则创建一个新的事务。
     * 嵌套事务是外部事务的一部分(外部事务的子事务)，外部事务回滚时，嵌套事务也会回滚，但嵌套事务回滚不会影响外部事务
     */
    @Transactional(propagation = Propagation.NESTED)
    public void nested() {

    }

    /**
     * 如果当前存在事务，则在当前事务中运行，否则以非事务方式运行
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supports() {

    }

    /**
     * 以非事务方式运行，如果当前存在事务，则挂起当前事务
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupported() {

    }

    /**
     * 必须在当前存在事务中运行，否则抛出异常(必须是已有事务 并且加入到已有事务 否则报错)
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatory() {

    }

    /**
     * 以非事务方式运行，如果当前存在事务，则抛出异常
     */
    @Transactional(propagation = Propagation.NEVER)
    public void never() {

    }

    /**
     * Transactional注解不指定 propagation 属性时，默认的事务传播行为是 Propagation.REQUIRED
     */
    @Transactional
    public void call() {
        required();
    }
}
