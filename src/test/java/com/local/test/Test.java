package com.local.test;

import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/22 12:45
 */
public class Test {

    public static void main(String[] args) {
        Hello target = new HelloImpl();
        Interceptor myInterceptor = new MyInterceptor();
        target = (Hello) myInterceptor.plugin(target);
        target.sayHello();
    }

}
