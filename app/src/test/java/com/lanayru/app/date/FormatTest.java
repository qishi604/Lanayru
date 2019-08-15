package com.lanayru.app.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019-07-05
 */
public class FormatTest {

    @Test
    public void testFormat() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date()));
    }
}
