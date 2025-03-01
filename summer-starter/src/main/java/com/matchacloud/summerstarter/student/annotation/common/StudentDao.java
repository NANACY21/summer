package com.matchacloud.summerstarter.student.annotation.common;

import org.springframework.stereotype.Repository;

/**
 * 用于标注数据访问层的组件，如 DAO（数据访问对象）类，Spring 会对其进行异常转换等操作
 */
@Repository
public class StudentDao {
    public String getData() {
        return "Data from repository";
    }
}
