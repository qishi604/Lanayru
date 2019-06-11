package com.lanayru.view

import android.app.Activity
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
class NavBarHolderView : View {

    var mNavBarVisible = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        mNavBarVisible = isNavBarVisible()
        viewTreeObserver.addOnGlobalLayoutListener {
            val visible = isNavBarVisible()
            if (mNavBarVisible != visible) {
                requestLayout()
                mNavBarVisible = visible
            }
        }
    }

    private fun isNavBarVisible() = BarUtils.isNavBarVisible(context as Activity)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        println("onMeasure")

        val w = ScreenUtils.getScreenWidth()
        val h = if (mNavBarVisible) BarUtils.getNavBarHeight() else 0
        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        println("onlayout l $left t $top r $right b $bottom")
    }
}