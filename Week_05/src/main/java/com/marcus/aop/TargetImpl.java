package com.marcus.aop;

import java.lang.reflect.Proxy;

public class TargetImpl implements Target {

    @Override
    public String hello(String s) {
        System.out.println("target.hello called, args is "+s);
        return s;
    }

    public static void main(String[] args) {
        TargetImpl target = new TargetImpl();
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class[] interfaces = target.getClass().getInterfaces();
        ProxyHandler handler = new ProxyHandler(target);
        Target proxy = (Target) Proxy.newProxyInstance(classLoader, interfaces, handler);
        proxy.hello("Marcus");
    }
}
