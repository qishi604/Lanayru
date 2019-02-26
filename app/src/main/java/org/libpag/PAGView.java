package org.libpag;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.animation.LinearInterpolator;

public class PAGView extends TextureView implements SurfaceTextureListener {
    private boolean _isPlaying = false;
    private ValueAnimator animator;
    private boolean isAttachedToWindow = false;
    private SurfaceTextureListener mListener;
    private PAGRenderer pagRenderer;
    private PAGSurface pagSurface;

    public PAGView(Context context) {
        super(context);
        setupSurfaceTexture();
    }

    public PAGView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupSurfaceTexture();
    }

    public PAGView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setupSurfaceTexture();
    }

    private void setupSurfaceTexture() {
        setOpaque(false);
        this.pagRenderer = new PAGRenderer();
        setSurfaceTextureListener(this);
        this.animator = ValueAnimator.ofFloat(new float[]{0, 1.0f});
        this.animator.setRepeatCount(0);
        this.animator.setInterpolator(new LinearInterpolator());
        this.animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PAGView.this.pagRenderer.setProgress((double) ((Float) valueAnimator.getAnimatedValue()).floatValue());
                PAGView.this.flush();
            }
        });
        this.animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                PAGView.this._isPlaying = false;
            }
        });
    }

    public void setSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        if (surfaceTextureListener == this) {
            super.setSurfaceTextureListener(surfaceTextureListener);
        } else {
            this.mListener = surfaceTextureListener;
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.pagSurface = PAGSurface.FromSurfaceTexture(surfaceTexture);
        this.pagRenderer.setSurface(this.pagSurface);
        flush();
        if (this.mListener != null) {
            this.mListener.onSurfaceTextureAvailable(surfaceTexture, i, i2);
        }
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.pagSurface != null) {
            this.pagSurface.updateSize();
            flush();
        }
        if (this.mListener != null) {
            this.mListener.onSurfaceTextureSizeChanged(surfaceTexture, i, i2);
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.pagSurface = null;
        this.pagRenderer.setSurface(null);
        if (this.mListener != null) {
            this.mListener.onSurfaceTextureDestroyed(surfaceTexture);
        }
        return true;
    }

    public void onAttachedToWindow() {
        this.isAttachedToWindow = true;
        super.onAttachedToWindow();
        if (this._isPlaying) {
            doPlay();
        }
    }

    protected void onDetachedFromWindow() {
        this.isAttachedToWindow = false;
        super.onDetachedFromWindow();
        if (this._isPlaying) {
            this.animator.cancel();
        }
        freeCache();
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.mListener != null) {
            this.mListener.onSurfaceTextureUpdated(surfaceTexture);
        }
    }

    public boolean isPlaying() {
        return this._isPlaying;
    }

    public void play() {
        this._isPlaying = true;
        doPlay();
    }

    private void doPlay() {
        if (this.isAttachedToWindow) {
            this.animator.setCurrentPlayTime((long) (this.pagRenderer.getProgress() * ((double) this.animator.getDuration())));
            this.animator.start();
        }
    }

    public void stop() {
        this._isPlaying = false;
        this.animator.cancel();
    }

    public void setLoop(boolean z) {
        this.animator.setRepeatCount(z ? -1 : 0);
    }

    public void setRepeatCount(int i) {
        this.animator.setRepeatCount(i);
    }

    public void addListener(AnimatorListener animatorListener) {
        this.animator.addListener(animatorListener);
    }

    public void removeListener(AnimatorListener animatorListener) {
        this.animator.removeListener(animatorListener);
    }

    public long duration() {
        PAGFile file = this.pagRenderer.getFile();
        return file == null ? 0 : file.duration();
    }

    public PAGFile getFile() {
        return this.pagRenderer.getFile();
    }

    public void setFile(PAGFile pAGFile) {
        this.animator.setDuration((pAGFile != null ? pAGFile.duration() : 0) / 1000);
        this.pagRenderer.setFile(pAGFile);
    }

    public double getProgress() {
        return this.pagRenderer.getProgress();
    }

    public void setProgress(double d) {
        this.pagRenderer.setProgress(d);
    }

    public int scaleMode() {
        return this.pagRenderer.scaleMode();
    }

    public void setScaleMode(int i) {
        this.pagRenderer.setScaleMode(i);
    }

    public Matrix matrix() {
        return this.pagRenderer.matrix();
    }

    public void setMatrix(Matrix matrix) {
        this.pagRenderer.setMatrix(matrix);
    }

    public void setTextData(int i, PAGText pAGText) {
        this.pagRenderer.setTextData(i, pAGText);
    }

    public boolean flush() {
        return this.pagRenderer.flush();
    }

    public void freeCache() {
        this.pagRenderer.freeCache();
        if (this.pagSurface != null) {
            this.pagSurface.freeCache();
        }
    }
}
