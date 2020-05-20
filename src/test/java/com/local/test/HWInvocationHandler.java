package com.local.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/22 12:39
 */
public class HWInvocationHandler implements InvocationHandler {

    private Object target;

    private Interceptor interceptor;

    public HWInvocationHandler(Object target,Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation(target,method,args);
        return interceptor.intercept(invocation);
    }

    public static Object wrap(Object target,Interceptor interceptor) {
        HWInvocationHandler targetProxy = new HWInvocationHandler(target, interceptor);
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),target.getClass().getInterfaces(),targetProxy);
    }
}
