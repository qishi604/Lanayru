package com.lanayru.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019/6/10
 */
public class Chronometer extends android.support.v7.widget.AppCompatTextView {

    private static final String TAG = "Chronometer";

    /**
     * 更新文字回调
     */
    public interface OnTickListener {

        /**
         * 计时，每秒回调，用于更新文字，这里单位是毫秒，如果要转换成秒可以除以 1000
         * @param ms 毫秒
         */
        void onTick(long ms);

        /**
         * 停止
         */
        void onStop();
    }

    private long mBase;
    private long mNow; // the currently displayed time
    private boolean mVisible;
    private boolean mStarted;
    private boolean mRunning;
    private OnTickListener mOnTickListener;
    private boolean mCountDown;

    private long mMillisInFuture;

    private long mStopTimeInFuture;

    /**
     * Initialize this Chronometer object.
     * Sets the base to the current time.
     */
    public Chronometer(Context context) {
        this(context, null, 0);
    }

    /**
     * Initialize with standard view layout information.
     * Sets the base to the current time.
     */
    public Chronometer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Initialize with standard view layout information and style.
     * Sets the base to the current time.
     */
    public Chronometer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBase = SystemClock.elapsedRealtime();
        updateText(mBase);
    }

    /**
     * Set this view to count down to the base instead of counting up from it.
     *
     * @param countDown whether this view should count down
     *
     * @see #setBase(long)
     */
    public void setCountDown(boolean countDown) {
        mCountDown = countDown;
        updateText(SystemClock.elapsedRealtime());
    }

    /**
     * @return whether this view counts down
     *
     * @see #setCountDown(boolean)
     */
    public boolean isCountDown() {
        return mCountDown;
    }

    /**
     * Set the time that the count-up timer is in reference to.
     *
     * @param base Use the {@link SystemClock#elapsedRealtime} time base.
     */
    private void setBase(long base) {
        mBase = base;
        updateText(SystemClock.elapsedRealtime());
    }

    /**
     * Return the base time as set through {@link #setBase}.
     */
    public long getBase() {
        return mBase;
    }

    /**
     * 设置到达 ms 毫秒后自动停止
     * @param ms 毫秒
     */
    public void setMillisInFuture(long ms) {
        mMillisInFuture = ms;
    }

    public void setOnTickListener(OnTickListener listener) {
        mOnTickListener = listener;
    }

    /**
     * 重新开始，第一次最好也直接调这个
     */
    public void restart() {
        final long realTime = SystemClock.elapsedRealtime();
        mStopTimeInFuture = realTime + mMillisInFuture;
        setBase(realTime);
        start();
    }

    /**
     * Start counting up.  This does not affect the base as set from {@link #setBase}, just
     * the view display.
     *
     * Chronometer works by regularly scheduling messages to the handler, even when the
     * Widget is not visible.  To make sure resource leaks do not occur, the user should
     * make sure that each start() call has a reciprocal call to {@link #stop}.
     */
    public void start() {
        mStarted = true;
        updateRunning();
    }

    /**
     * Stop counting up.  This does not affect the base as set from {@link #setBase}, just
     * the view display.
     *
     * This stops the messages to the handler, effectively releasing resources that would
     * be held as the chronometer is running, via {@link #start}.
     */
    public void stop() {
        mStarted = false;
        if (null != mOnTickListener) {
            mOnTickListener.onStop();
        }
        updateRunning();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mVisible = false;
        updateRunning();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        mVisible = visibility == VISIBLE;
        updateRunning();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        updateRunning();
    }

    private synchronized void updateText(long now) {
        mNow = now;
        long seconds = mCountDown ? mBase - now : now - mBase;
        if (null != mOnTickListener) {
            mOnTickListener.onTick(seconds);
        }
    }

    private void updateRunning() {
        boolean running = mVisible && mStarted && isShown();
        if (running != mRunning) {
            if (running) {
                updateText(SystemClock.elapsedRealtime());
                postDelayed(mTickRunnable, 1000);
            } else {
                removeCallbacks(mTickRunnable);
            }
            mRunning = running;
        }
    }

    private final Runnable mTickRunnable = new Runnable() {
        @Override
        public void run() {
            if (mRunning) {
                final long realTime = SystemClock.elapsedRealtime();
                if (realTime >= mStopTimeInFuture) {
                    stop();
                    return;
                }

                updateText(realTime);
                postDelayed(mTickRunnable, 1000);
            }
        }
    };
}
