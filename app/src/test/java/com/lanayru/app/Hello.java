package com.lanayru.app;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/4/28
 */
public class Hello {

    int foo(int a, int b) {
        return (a + b) * (a - b);
    }

    public static void main(String[] args) {
        Hello h = new Hello();
        final int f = h.foo(5, 4);
        System.out.println(f);   
    }
}
