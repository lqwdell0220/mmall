package com.local.test;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/22 12:22
 */
public interface Interceptor {

    /**
     * 具体拦截处理
     */
    Object intercept(Invocation invocation) throws Exception;

    /**
     *  插入目标类
     */
    Object plugin(Object target);
}
