package com.lanayru.view;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.lanayru.app.R;

public class Keyboard {

    private static final int HEIGHT_MIN = SizeUtils.dp2px(40);

    private Activity mActivity;

    private View mContentChild;

    private int mUsableHeightPrevious;

    private View mLKeyboard;

    private View mLPlaceHolder;

    private ImageView mIvRecord;

    private EditText mEtMessage;

    private TextView mTvRecord;

    private View mLFace;

    private View mLAdd;

    private ImageView mIvFace;

    private ImageView mIvAdd;

    private int mKeyboardHeight;

    private FrameLayout.LayoutParams mContentLp;

    private int FLAG_RECORD = 1;

    private int FLAG_FACE = 1 << 1;

    private int FLAG_ADD = 1 << 2;

    private int mShowFlag = 0;

    public void assistActivity(Activity activity, View keyboardView) {
        mActivity = activity;
        ViewGroup contentView = activity.findViewById(android.R.id.content);
        mContentChild = contentView.getChildAt(0);
        mContentLp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
        mContentChild.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });

        mLKeyboard = keyboardView;
        mLPlaceHolder = mLKeyboard.findViewById(R.id.l_holder);

        mIvRecord = mLKeyboard.findViewById(R.id.iv_voice);
        mEtMessage = mLKeyboard.findViewById(R.id.et_content);
        mTvRecord = mLKeyboard.findViewById(R.id.tv_record);
        mLFace = mLKeyboard.findViewById(R.id.l_face);
        mLAdd = mLKeyboard.findViewById(R.id.l_add);
        mIvFace = mLKeyboard.findViewById(R.id.iv_face);
        mIvAdd = mLKeyboard.findViewById(R.id.iv_add);

        mIvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecord();
            }
        });

        mEtMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyboard();
            }
        });

        mIvFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFace();
            }
        });

        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd();
            }
        });
    }

    private void showRecord() {
        mShowFlag = FLAG_RECORD;

        if (isKeyboardVisible()) {
            hideKeyboard();
        } else {
            onKeyboardHide();
        }
    }

    private void showFace() {
        mShowFlag = FLAG_FACE;

        if (isKeyboardVisible()) {
            hideKeyboard();
        } else {
            onKeyboardHide();
        }
    }

    private void showAdd() {
        mShowFlag = FLAG_ADD;

        if (isKeyboardVisible()) {
            hideKeyboard();
        } else {
            onKeyboardHide();
        }
    }

    private void showKeyboard() {
        KeyboardUtils.showSoftInput(mEtMessage);
    }

    private void hideKeyboard() {
        KeyboardUtils.hideSoftInput(mEtMessage);
    }

    private void hideViews() {
        mShowFlag = 0;

        mLPlaceHolder.setVisibility(View.GONE);
        mLFace.setVisibility(View.GONE);
        mLAdd.setVisibility(View.GONE);

    }

    private void possiblyResizeChildOfContent() {
        final Rect r = new Rect();
        mContentChild.getWindowVisibleDisplayFrame(r);
//        final int usableHeightNow = r.bottom - r.top;
        final int usableHeightNow = r.bottom; // 假设他们 top 一致
        if (usableHeightNow != mUsableHeightPrevious) {
            final int usableHeightSansKeyboard = mContentChild.getRootView().getBottom();
            final int heightDifference = usableHeightSansKeyboard - usableHeightNow;

            int h = 0;
            if (heightDifference > HEIGHT_MIN) {
                // keyboard probably just became visible
                h = usableHeightSansKeyboard - heightDifference;

                mKeyboardHeight = heightDifference;
                onKeyboardShow(mKeyboardHeight);
            } else {
                // keyboard probably just became hidden
                h = usableHeightSansKeyboard;

                onKeyboardHide();
            }
            mUsableHeightPrevious = usableHeightNow;

//            mContentLp.height = (r.bottom - r.top); // 不能直接这样设置，页面会抖动

//            mContentLp.bottomMargin = heightDifference;
//            mContentChild.requestLayout();

//            mContentChild.setTranslationY(-heightDifference); // 这种方式页面会抖动

            // 试试padding，居然没抖动 ~~~
            mContentChild.setPadding(0, 0, 0, heightDifference);

        }
    }

    private void onKeyboardShow(int keyboardHeight) {
        hideViews();

        mLPlaceHolder.getLayoutParams().height = keyboardHeight;
    }

    private void onKeyboardHide() {
        if (isFlag(FLAG_RECORD)) {
            mLPlaceHolder.setVisibility(View.GONE);
            mTvRecord.setVisibility(View.VISIBLE);
            mEtMessage.setVisibility(View.GONE);

        } else if (isFlag(FLAG_FACE)) {
            mTvRecord.setVisibility(View.GONE);
            mEtMessage.setVisibility(View.VISIBLE);

            mLPlaceHolder.setVisibility(View.VISIBLE);
            mLFace.setVisibility(View.VISIBLE);
            mLAdd.setVisibility(View.GONE);

        } else if (isFlag(FLAG_ADD)) {
            mTvRecord.setVisibility(View.GONE);
            mEtMessage.setVisibility(View.VISIBLE);

            mLPlaceHolder.setVisibility(View.VISIBLE);
            mLFace.setVisibility(View.GONE);
            mLAdd.setVisibility(View.VISIBLE);

        } else {
            mLPlaceHolder.setVisibility(View.GONE);
        }
    }

    private boolean isKeyboardVisible() {
        return KeyboardUtils.isSoftInputVisible(mActivity);
    }

    private boolean isFlag(int flag) {
        return (mShowFlag & flag) == flag;
    }
}
