package com.lanayru.util;

public class MemoryLog {

    private static final long M = 1024*1024;

    /**
     * 现在图片大部分的内存都在 native，java 层只占小部分，这个方法得出的数据意义不大
     */
    public static void log() {
        Runtime runtime = Runtime.getRuntime();
        long javaMax = runtime.maxMemory();
        long javaTotal = runtime.totalMemory();
        long javaUsed = javaTotal - runtime.freeMemory();

        // 内存使用量
        float proportion = javaUsed * 1f / javaMax;

        Logs.INSTANCE.e("javaMax： " + format(javaMax) + " javaTotal: " + format(javaTotal) + " javaUsed： "+ format(javaUsed) + " proportion: " + proportion);
    }

    private static String format(long v) {
        float result = v * 1f / M;
        return String.format("%.2f M", result);
    }
}
