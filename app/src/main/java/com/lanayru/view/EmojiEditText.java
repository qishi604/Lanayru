package com.lanayru.view;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;

/**
 * @author 郑齐
 * @version V1.0
 * @since 2019-07-22
 */
public class EmojiEditText extends android.support.v7.widget.AppCompatEditText {
    public EmojiEditText(Context context) {
        super(context);
    }

    public EmojiEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmojiEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        boolean b = super.onTextContextMenuItem(id);
        if (id == android.R.id.paste || id == android.R.id.pasteAsPlainText) {
            onPaste();
        }

        return b;
    }


    final int maxLength = 10;

    private void onPaste() {
        final Editable text = getText();
        if (text.length() == maxLength) {
            final String s = text.toString();
            for (int i = maxLength - 1, N = maxLength - 6; i >= N; i--) {
                final char c = s.charAt(i);
                if (c == ']') {
                    return;
                }

                if (c == '[') {
                    text.delete(i, maxLength);
                    return;
                }
            }
        }
    }
}
