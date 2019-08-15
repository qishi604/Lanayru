package com.lanayru.app.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lanayru.app.R;
import com.lanayru.library.ui.base.BaseActivity;
import com.lanayru.extention.AndroidExtensionsKt;

import org.jetbrains.annotations.Nullable;

/**
 * @author seven
 * @version V1.0
 * @since 2019/3/7
 */
public class LayoutActivity extends BaseActivity {

    private LinearLayout mLContent;

    private View mVAdd;

    @Override
    public void render(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_layout);

        mLContent = findViewById(R.id.l_content);
        mVAdd = findViewById(R.id.v_add);

        mVAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChild();
            }
        });
    }

    private View.OnClickListener mDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getParent() instanceof ViewGroup) {
                ((ViewGroup)v.getParent()).removeView(v);
            }
        }
    };

    private void addChild() {
        ImageView view = new ImageView(this);
        view.setBackgroundColor(0xff889900);
        final int w = AndroidExtensionsKt.getDp(40);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w);
        lp.rightMargin = AndroidExtensionsKt.getDp(10);

        view.setOnClickListener(mDelete);


        mLContent.addView(view, lp);
    }
}
