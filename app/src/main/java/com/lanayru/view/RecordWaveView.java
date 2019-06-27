package com.lanayru.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class RecordWaveView extends View {

    private Paint mPaint;

    private int mBase = 20;

    private int w = SizeUtils.dp2px(4);

    private int space = SizeUtils.dp2px(4);



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
        mBase = base;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLine(canvas);
    }

    private void drawLine(Canvas canvas) {
        List<Integer> list = random(mBase);
        final int N = list.size();
        float x = 0;
        float endY = 0;
        for (int i = 0; i < N; i++) {
            x += w + space;
            endY = y(list.get(i));
            canvas.drawLine(x, 0, x, endY, mPaint);
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
        final int size = 20;
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int v = base + (int) (Math.random() * base * 0.2);
            list.add(v);
        }

        return list;
    }
}
