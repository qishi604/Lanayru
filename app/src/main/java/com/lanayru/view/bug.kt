package com.lanayru.view

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout


/**
 *
 * 键盘遮挡解决方案，未经过多机型测试。
 * 如果以后有需求，可以考虑将EditText 放到dialog
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/16
 *
 **/

class AndroidBug5497Workaround private constructor(activity: Activity) {

    private val mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private val frameLayoutParams: FrameLayout.LayoutParams

    var onShow: (()->Unit)? = null

    var onHide: (()->Unit)? = null

    init {
        val content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener { possiblyResizeChildOfContent() })
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow

            var h = 0
            if (heightDifference > 40) {
                // keyboard probably just became visible
                 h = usableHeightSansKeyboard - heightDifference
                onShow?.invoke()
            } else {
                // keyboard probably just became hidden
                h = usableHeightSansKeyboard
                onHide?.invoke()

            }
            usableHeightPrevious = usableHeightNow

//            frameLayoutParams.height = h
//            frameLayoutParams.bottomMargin = heightDifference

            mChildOfContent.translationY = - heightDifference.toFloat()

            mChildOfContent.requestLayout()
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }

    companion object {

        // For more information, see https://issuetracker.google.com/issues/36911528
        // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

        fun assistActivity(activity: Activity): AndroidBug5497Workaround {
            return AndroidBug5497Workaround(activity)
        }
    }
}