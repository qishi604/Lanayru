package com.lanayru.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.lanayru.util.Logs

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/16
 *
 **/
class RelativeRoot: RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Logs.i("onMeasure width: $widthMeasureSpec , height: $heightMeasureSpec")
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        Logs.i("onLayout $l , $t, $r, $b")

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Logs.i("onDraw ===== ")
    }
}