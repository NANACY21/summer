package com.matchacloud.basic.acm.acm20250220;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 线路
 *
 * @since 2025-03-04
 */
@Data
@AllArgsConstructor
public class Route {

    //线路终点 第x行
    private int x;

    //线路终点 第y行
    private int y;

    //当前线路评分
    private int score;
}
