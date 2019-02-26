package org.libpag;

import android.graphics.SurfaceTexture;
import android.view.Surface;

public class PAGSurface {
    long nativeSurface = 0;
    private Surface surface = null;

    private static native long SetupFromSurface(Surface surface);

    private static native void nativeInit();

    private native void nativeRelease();

    public native void clear();

    public native void freeCache();

    public native int height();

    public native boolean present();

    public native void updateSize();

    public native int width();

    public static PAGSurface FromSurface(Surface surface) {
        if (surface == null) {
            return null;
        }
        long SetupFromSurface = SetupFromSurface(surface);
        if (SetupFromSurface != 0) {
            return new PAGSurface(surface, SetupFromSurface);
        }
        return null;
    }

    public static PAGSurface FromSurfaceTexture(SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            return null;
        }
        return FromSurface(new Surface(surfaceTexture));
    }

    private PAGSurface(Surface surface, long j) {
        this.surface = surface;
        this.nativeSurface = j;
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
