package org.libpag;

import android.content.res.AssetManager;

public class PAGFile {
    long nativeContext = 0;

    public class LayerType {
        public static final int Image = 5;
        public static final int Null = 1;
        public static final int PreCompose = 6;
        public static final int Shape = 4;
        public static final int Solid = 2;
        public static final int Text = 3;
        public static final int Unknown = 0;
    }

    private static native long LoadFromAssets(AssetManager assetManager, String str);

    private static native long LoadFromPath(String str);

    private static final native void nativeInit();

    private final native void nativeRelease();

    private native void nativeReplaceImage(long j, long j2);

    public native long duration();

    public native PAGFont[] getFontList();

    public native long[] getImageIDList();

    public native int getLayerType(int i);

    public native PAGText getTextData(int i);

    public native int height();

    public native int numLayers();

    public native void setTextData(int i, PAGText pAGText);

    public native int width();

    public static PAGFile Load(String str) {
        long LoadFromPath = LoadFromPath(str);
        if (LoadFromPath == 0) {
            return null;
        }
        return new PAGFile(LoadFromPath);
    }

    public static PAGFile Load(AssetManager assetManager, String str) {
        long LoadFromAssets = LoadFromAssets(assetManager, str);
        if (LoadFromAssets == 0) {
            return null;
        }
        return new PAGFile(LoadFromAssets);
    }

    private PAGFile(long j) {
        this.nativeContext = j;
    }

    public void replaceImage(long j, PAGPixelData pAGPixelData) {
        nativeReplaceImage(j, pAGPixelData.nativePixelData);
    }

    protected void finalize() {
        nativeRelease();
    }

    static {
        System.loadLibrary("libpag");
        nativeInit();
        PAGFont.loadSystemFonts();
    }
}
