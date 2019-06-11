package com.lanayru.app.encrypt;

import com.blankj.utilcode.util.EncryptUtils;

import org.junit.Test;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/5/31
 */
public class EncryptionTest {

    @Test
    public void testSA1() {
        String s = "123456";
        final String s1 = EncryptionUtil.encodePassword(s);
        final String s2 = EncryptUtils.encryptSHA1ToString(s).toLowerCase();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1.equals(s2));
    }
}
