package com.lanayru.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019-08-05
 */
public class TouchTestView extends android.support.v7.widget.AppCompatButton {

    private boolean mEnableRecord;

    public TouchTestView(Context context) {
        this(context, null);
    }

    public TouchTestView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TouchTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("TouchTestView Click");
            }
        });

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                System.out.println("TouchTestView Long click");
                mEnableRecord = true;
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mEnableRecord) {
            return super.onTouchEvent(event);
        }

        System.out.println("Touch Event" + event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                mEnableRecord = false;
                break;
        }
        return true;
    }
}
