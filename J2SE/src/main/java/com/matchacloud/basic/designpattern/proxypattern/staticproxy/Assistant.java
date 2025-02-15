package com.matchacloud.basic.designpattern.proxypattern.staticproxy;

import lombok.AllArgsConstructor;

/**
 * 代理类 明星助理
 */
@AllArgsConstructor
public class Assistant implements Signable {

    private Star star;

    /**
     * 签名
     */
    @Override
    public void sign() {
        System.out.println("助理接收粉丝签名请求，安排时间");
        star.sign();
        System.out.println("助理把签好名的物品交给粉丝");
    }

    public static void main(String[] args) {
        // 创建明星对象
        Star star = new Star();
        // 创建代理对象（助理），并将明星对象传入
        Assistant assistant = new Assistant(star);
        // 助理代理明星进行签名
        assistant.sign();
    }
}
