package org.libpag;

import java.nio.ByteBuffer;

public class PAGPixelData {
    long nativePixelData = 0;

    public static native PAGPixelData FromBGRA(ByteBuffer byteBuffer, int i, int i2, int i3);

    public static native PAGPixelData FromPath(String str);

    public static native PAGPixelData FromRGBA(ByteBuffer byteBuffer, int i, int i2, int i3);

    private static final native void nativeInit();

    private final native void nativeRelease();

    protected void finalize() {
        nativeRelease();
    }

    static {
        System.loadLibrary("libpag");
        nativeInit();
        PAGFont.loadSystemFonts();
    }
}
