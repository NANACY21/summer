package com.matchacloud.utils;

import com.matchacloud.entity.BaseEntity;

import java.time.LocalDateTime;

public class FillFieldUtil {
    public static BaseEntity fillField(BaseEntity entity) {
        entity.setCreateTime(LocalDateTime.now());

        return entity;
    }
}
