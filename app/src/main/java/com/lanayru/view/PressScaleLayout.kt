package com.lanayru.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import com.lanayru.util.Logs

class PressScaleLayout : FrameLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Logs.i("onMeasure")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Logs.i("onLayout change: $changed, left: $left, top: $top, right: $right, bottom: $bottom")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        Logs.i("onSize change w: $w, h: $h, oldw: $oldw, oldh: $oldh")
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Logs.i("onDraw")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (null == event) {
            return super.onTouchEvent(event)
        }

        val action = event.action

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                onDown()
                return true
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP-> {
                onRelease()
            }
        }

        return super.onTouchEvent(event)
    }

    private var mOutAnim: ViewPropertyAnimator? = null
    private var mInAnim: ViewPropertyAnimator? = null
    private fun onDown() {
        mOutAnim = animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).setInterpolator(AccelerateDecelerateInterpolator())
        mOutAnim!!.start()
    }

    private fun onRelease() {
        mInAnim = animate().scaleX(1f).scaleY(1f).setDuration(100).setInterpolator(AccelerateDecelerateInterpolator())
        mInAnim!!.start()
    }
}