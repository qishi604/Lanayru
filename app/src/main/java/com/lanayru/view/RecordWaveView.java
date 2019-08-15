package com.lanayru.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

import java.util.Random;

/**
 * 变化太直接显得动画比较生硬，可以做个过渡
 */
public class RecordWaveView extends View {

    private static final int DEFAULT_LINE_COUNT = 30;

    private Paint mPaint;

    private int mAnimBase = 20;

    private int mLineWidth = SizeUtils.dp2px(2);

    private int mLineSpace = SizeUtils.dp2px(4);

    private int mRadius = mLineWidth / 2;

    private int mLineCount = DEFAULT_LINE_COUNT;

    private ValueAnimator valueAnimator;

    private long mAnimDuration = 400;

    static Random mRandom = new Random();

    public RecordWaveView(Context context) {
        this(context, null);
    }

    public RecordWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
//        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setAnimDuration(long duration) {
        mAnimDuration = duration;
    }

    public void setBase(int base) {
//        if (null == valueAnimator) {
//            valueAnimator = ValueAnimator.ofInt(mAnimBase, base);
//            valueAnimator.setDuration(mAnimDuration);
//            valueAnimator.setInterpolator(new LinearInterpolator());
//            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    final int f = (int) animation.getAnimatedValue();
//                    refresh(f);
//                }
//            });
//
//        } else {
//
//            valueAnimator.cancel();
//            final int animatedValue = (int) valueAnimator.getAnimatedValue();
//            valueAnimator.setIntValues(animatedValue, base);
//        }
//
//        valueAnimator.start();

        refresh(base);
    }

    private void refresh(int base) {
        mAnimBase = base >= 10 ? base : 10;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int measuredWidth = getMeasuredWidth();
        mLineCount = (measuredWidth - mLineSpace) / (mLineWidth + mLineSpace);

        final int w = mLineCount * (mLineWidth + mLineSpace) + mLineSpace;
        if (measuredWidth != w) {
            setMeasuredDimension(w, getMeasuredHeight());
        }
    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        final int width = getWidth();
//        mLineCount = width / (mLineWidth + mLineSpace);
//        final int allLineWidth = (mLineWidth * mLineCount) + mLineSpace * (mLineCount - 1);
//        mX = (width - allLineWidth) / 2;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLines(canvas);
    }

    private void drawLines(Canvas canvas) {
        int[] list = random(mAnimBase, mLineCount);
        final int N = list.length;
        float l = mLineSpace;
        float t = 0;
        float r = 0;
        float b = 0;
        final int H = getHeight();

        // for fun
//        final int color = ColorUtils.getRandomColor(false);
//        mPaint.setColor(color);

        for (int i = 0; i < N; i++) {
            if (i > 0) {
                l += mLineWidth + mLineSpace;
            }
            float h = y(list[i]);
            t = (H - h) / 2;
            r = l + mLineWidth;
            b = t + h;

//            canvas.drawLine(x, startY, x, endY, mPaint);
            RectF rectF = new RectF(l, t, r, b);
            canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
        }
    }

    private float y(int base) {
        if (base > 100) {
            base = 100;
        } else if (base < 10) {
            base = 10 - mRandom.nextInt(5);
        }

        return getHeight() * base / 100.f;
    }

    /**
     * <pre>
     * 1. 先随机
     * 2. 两头挑 4 个用小的随机数替换
     * 3. 中间挑几个用 base 替换
     * </pre>
     *
     * @param base 基数
     * @param size 总量
     * @return int[]
     */
    public static int[] random(int base, int size) {
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
//            int v = (int) (base * (1 - Math.random() * 0.2));
            int v = mRandom.nextInt(base);
            data[i] = v;
        }

        int side = size / 8;
        side = Math.min(4, side);

        // 两边
        for (int i = 0; i < side; i++) {
//            int v = (int) (base * (1 - Math.random() * 0.8));
            int v = (int) (mRandom.nextInt(base) * 0.4f);
            data[i] = v;

            data[size - 1 - i] = v;
        }

        // 中间
        for (int i = 0, N = size / 4; i < N; i++) {
            int index = mRandom.nextInt(size - side * 2) + side;
            int d = (int) (base * 0.2f);
            if (index < size) {
                int v = base - mRandom.nextInt(d);
                data[index] = v;
            }
        }

        return data;
    }
}
