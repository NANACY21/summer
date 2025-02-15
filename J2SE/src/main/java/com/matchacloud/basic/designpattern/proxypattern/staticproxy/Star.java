package com.matchacloud.basic.designpattern.proxypattern.staticproxy;

/**
 * 明星
 */
public class Star implements Signable {

    /**
     * 签名
     */
    @Override
    public void sign() {
        System.out.println("明星正在签名");
    }
}
