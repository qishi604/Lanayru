package com.lanayru.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 变化太直接显得动画比较生硬，可以做个过渡
 */
public class RecordWaveView extends View {

    private static final int DEFAULT_SIZE = 30;

    private Paint mPaint;

//    private int mBase = 20;

    private int mAnimBase = 20;

    private int w = SizeUtils.dp2px(4);

    private int space = SizeUtils.dp2px(4);

    private ValueAnimator valueAnimator;

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
        mPaint.setColor(0xff888888);
        mPaint.setStrokeWidth(w);
    }

    public void setBase(int base) {
        if (null == valueAnimator) {
            valueAnimator = ValueAnimator.ofInt(mAnimBase, base);
            valueAnimator.setDuration(400);
            valueAnimator.setInterpolator(new LinearInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    final int f = (int) animation.getAnimatedValue();
                    refresh(f);
                }
            });

        } else {

            valueAnimator.cancel();
            final int animatedValue = (int) valueAnimator.getAnimatedValue();
            valueAnimator.setIntValues(animatedValue, base);
        }

        valueAnimator.start();
    }

    private void refresh(int base) {
        mAnimBase = base;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLines(canvas);
    }

    private void drawLines(Canvas canvas) {
        List<Integer> list = random(mAnimBase);
        final int N = list.size();
        float x = 0;
        float startY = 0;
        float endY = 0;
        final int H = getHeight();

        // for fun
//        final int color = ColorUtils.getRandomColor(false);
//        mPaint.setColor(color);

        for (int i = 0; i < N; i++) {
            x += w + space;
            float h = y(list.get(i));
            startY = (H - h) / 2;
            endY = startY + h;


            canvas.drawLine(x, startY, x, endY, mPaint);
        }
    }

    private float y(int base) {
        if (base > 100) {
            base = 100;
        } else if (base < 10) {
            base = 10;
        }

        return getHeight() * base / 100.f;
    }

    public static List<Integer> random(int base) {
        final int size = DEFAULT_SIZE;
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int v = base + (int) (Math.random() * base * 0.2);
            list.add(v);
        }

        return list;
    }
}
