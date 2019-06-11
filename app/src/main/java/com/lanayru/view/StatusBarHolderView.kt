package com.lanayru.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils

/**
 * @author 郑齐
 * @since 2019/5/30
 * @version V1.0
 *
 */
class StatusBarHolderView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = ScreenUtils.getScreenWidth()
        val h = BarUtils.getStatusBarHeight()
        setMeasuredDimension(w, h)
    }
}