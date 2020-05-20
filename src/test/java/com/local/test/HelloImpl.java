package com.local.test;

/**
 * @Discription
 * @Author:liqiwen
 * @Date:2020/4/22 12:47
 */
public class HelloImpl implements Hello{


    @Override
    public void sayHello() {
        System.out.println("Hello,我是被代理类的输出");
    }
}
