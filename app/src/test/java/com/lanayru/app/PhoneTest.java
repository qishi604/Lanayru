package com.lanayru.app;

import org.junit.Test;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/5/6
 */
public class PhoneTest {

    static String hidePhone(String phone) {
        if (null == phone || phone.length() < 11) {
            return phone;
        }

        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    @Test
    public void testPhone() {
        String s = "15072396547";
        System.out.println(hidePhone(s));

    }
}
