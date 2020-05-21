package com.local.test;

import java.lang.reflect.Proxy;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/22 12:49
 */
public class MyInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Exception {
        System.out.println("------插入前置通知代码-------------");
        Object result = invocation.process();
        System.out.println("------插入后置处理代码-------------");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        return HWInvocationHandler.wrap(target,this);
    }
}
