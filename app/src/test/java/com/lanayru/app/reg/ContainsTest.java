package com.lanayru.app.reg;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/4/4
 */
public class ContainsTest {

    public static boolean isEmpty(CharSequence s) {
        return null == s || s.length() == 0;
    }

    public boolean contains(String s, String key) {
        if (isEmpty(s) || isEmpty(key)) {
            return false;
        }

//        Pattern.compile(key, Pattern.CASE_INSENSITIVE);

        return Pattern.matches(key, s);
    }

    @Test
    public void test1() {
        String s = "12345678";
        String s1 = "123456788";
        String s2 = "12456788";
        String key = ".*123.*";

        System.out.println(contains(s, key));
        System.out.println(contains(s1, key));
        System.out.println(contains(s2, key));
    }

    @Test
    public void test2() {
        String s = "abcdefg";
        String s1 = "123adbc";
        String s2 = "ABcwe234";
        String key = ".*(?i)abc.*";

        System.out.println(contains(s, key));
        System.out.println(contains(s1, key));
        System.out.println(contains(s2, key));
    }
}
