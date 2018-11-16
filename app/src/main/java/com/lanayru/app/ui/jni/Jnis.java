package com.lanayru.app.ui.jni;

import android.graphics.Bitmap;

/**
 * @author seven
 * @version 1.0
 * @since 2018/9/25
 **/
public class Jnis {

    static {
        System.loadLibrary("util-lib");
    }

    public static native int max(int a, int b);

    public static native String getBitmapInfo(Bitmap bm);
}
