package com.lanayru.app.reg;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/6/6
 */
public class Highlight {

    private static class Range {
        int start;
        int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void print() {
            System.out.println("start: " + start + " end: " + end);
        }
    }

    private static Range find(CharSequence source, String key) {
        Pattern pattern = Pattern.compile(key, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            Range range = new Range(matcher.start(), matcher.end());
            return range;
        }

        return null;
    }

    @Test
    public void testFind() {
        String source = "123456+";
        String key = "+";

//        final Range range = find(source, key);
//        range.print();

        final int start = source.indexOf(key);
        if (start != -1) {
            new Range(start, start + key.length()).print();
        }

        System.out.println(source.contains(key));
    }
}
