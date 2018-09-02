package com.lanayru.view

import android.content.Context
import android.widget.FrameLayout

/**
 *
 * @author seven
 * @since 2018/7/20
 * @version V1.0
 */
class MFrameLayout(context: Context): FrameLayout(context) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Integer.toBinaryString(100)
    }
}