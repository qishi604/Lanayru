package com.lanayru.app;

import org.junit.Test;

/**
 * String 对象测试
 * @author 郑齐
 * @version V1.0
 * @since 2019/5/25
 */
public class StringObjTest {

    class City {
        String name;

        String code;

        public City(String name, String code) {
            this.name = name;
            this.code = code;
        }
    }

    @Test
    public void testEQ() {
        String a = "abc";
        String b = new String("abc");
        String c = a.intern();

        System.out.println(a == b); // false
        System.out.println(b == c); // false
        System.out.println(a == c); // true

    }


    @Test
    public void testCity() {
        String a = "abc";
        String b = new String("abc");
        String c = a.intern();

        City city1 = new City(a, "123");
        City city2 = new City(b, "123");

        System.out.println(city1.name == a); // true
        System.out.println(city2.name == b); // true
    }


}
