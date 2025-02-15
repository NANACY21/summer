package com.matchacloud.basic.designpattern.proxypattern.dynamicproxy;

import com.matchacloud.basic.designpattern.proxypattern.staticproxy.Signable;
import com.matchacloud.basic.designpattern.proxypattern.staticproxy.Star;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 * 现在假设这位明星有一个智能服务系统，当粉丝发起签名请求时，系统会动态地为明星分配一个临时助理来处理这个请求。
 * <p>
 * Spring 会根据目标对象是否实现接口来自动选择合适的动态代理方式
 * 当目标对象没有实现任何接口时，Spring AOP 会使用 CGLIB 动态代理
 */
public class StarProxyHandler implements InvocationHandler {

    private Object target;

    public StarProxyHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("智能服务系统分配临时助理，临时助理接收粉丝签名请求，安排时间");
        Object result = method.invoke(target, args);
        System.out.println("临时助理把签好名的物品交给粉丝");
        return result;
    }

    public static void main(String[] args) {
        // 创建明星对象
        Star star = new Star();
        // 创建 InvocationHandler 对象
        StarProxyHandler handler = new StarProxyHandler(star);
        // 动态创建代理对象
        Signable proxy = (Signable) Proxy.newProxyInstance(
                Star.class.getClassLoader(),
                Star.class.getInterfaces(),
                handler
        );
        // 代理对象进行签名操作
        proxy.sign();
    }
}
