package com.matchacloud.basic.designpattern.proxypattern.staticproxy;

/**
 * 代理模式是23种设计模式之一，是一种结构型设计模式
 * 代理模式允许通过代理对象来控制对另一个对象(目标对象)的访问。代理模式 通过调代理类来调目标方法 目的：解耦 定制化目标方法
 * 代理模式主要分为静态代理和动态代理
 * 动态代理分为 JDK动态代理 和 CGLIB动态代理
 */
public interface Signable {

    /**
     * 签名
     */
    void sign();
}
