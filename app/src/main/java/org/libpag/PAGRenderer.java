package org.libpag;

import android.graphics.Matrix;

public class PAGRenderer {
    private long nativeContext = 0;
    private PAGFile pagFile = null;
    private PAGSurface pagSurface = null;

    private native void nativeGetMatrix(float[] fArr);

    private native int nativeGetScaleMode();

    private static final native void nativeInit();

    private native void nativeSetFile(long j);

    private native void nativeSetScaleMode(int i);

    private native void nativeSetSurface(long j);

    private final native void nativeSetup();

    public native void draw();

    public native boolean flush();

    public native void freeCache();

    public native double getProgress();

    public final native void nativeRelease();

    public native void nativeSetMatrix(float f, float f2, float f3, float f4, float f5, float f6);

    public native void setProgress(double d);

    public native void setTextData(int i, PAGText pAGText);

    public PAGRenderer() {
        nativeSetup();
    }

    public PAGSurface getSurface() {
        return this.pagSurface;
    }

    public void setSurface(PAGSurface pAGSurface) {
        this.pagSurface = pAGSurface;
        if (pAGSurface == null) {
            nativeSetSurface(0);
        } else {
            nativeSetSurface(pAGSurface.nativeSurface);
        }
    }

    public PAGFile getFile() {
        return this.pagFile;
    }

    public void setFile(PAGFile pAGFile) {
        this.pagFile = pAGFile;
        if (pAGFile != null) {
            nativeSetFile(pAGFile.nativeContext);
        } else {
            nativeSetFile(0);
        }
    }

    public int scaleMode() {
        return nativeGetScaleMode();
    }

    public void setScaleMode(int i) {
        nativeSetScaleMode(i);
    }

    public Matrix matrix() {
        float[] fArr = new float[9];
        nativeGetMatrix(fArr);
        Matrix matrix = new Matrix();
        matrix.setValues(fArr);
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        nativeSetMatrix(fArr[0], fArr[3], fArr[1], fArr[4], fArr[2], fArr[5]);
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
