package com.local.test;

import java.lang.reflect.Method;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/22 12:22
 */
public class Invocation {
    /**
     * 目标对象
     */
    private Object target;
    /**
     * 执行的方法
     */
    private Method method;
    /**
     * 方法的参数
     */
    private Object[] args;

    //省略getset
    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    /**
     * 执行目标对象的方法
     */
    public Object process() throws Exception{
        return method.invoke(target,args);
    }
}
