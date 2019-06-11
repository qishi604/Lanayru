package com.lanayru.app.number;

import org.junit.Test;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/6/3
 */
public class LongIntTest {

    @Test
    public void test1() {
        int a= 1000000000;
        print(a);
    }

    public void print(long a) {
        System.out.println("a " + a);
    }
}
