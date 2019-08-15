package com.lanayru.util;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.ProcessUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019-07-05
 */
public class CrashLogger {

    private static boolean isSet = false;

    public static void setUncaughtExceptionHandler() {
        if (isSet) {
            return;
        }

        isSet = true;

        if (ProcessUtils.isMainProcess()) {
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    System.out.println(e);
                    final StackTraceElement[] stackTrace = e.getStackTrace();
                    StringBuilder sb = new StringBuilder();
                    for (StackTraceElement element : stackTrace) {
                        sb.append(element).append("\n");
                    }

                    String filename = "/mnt/sdcard/carchat/crashLog-" + new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss").format(new Date()) + ".txt";
                    FileIOUtils.writeFileFromString(filename, sb.toString(), true);
                }
            });
        }
    }
}
