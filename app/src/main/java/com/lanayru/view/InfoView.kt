package com.lanayru.view

import android.content.Context
import android.graphics.Canvas
import android.view.View

/**
 *
 * @author seven
 * @since 2018/7/18
 * @version V1.0
 */
class InfoView(context: Context): View(context) {

    var sb = StringBuilder("InfoView\n")

    init {
        sb.append("init\n")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        sb.append("onMeasure\n")
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        sb.append("onSizeChanged\n")

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        sb.append("onLayout\n")
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas?) {
        sb.append("onDraw\n")
        super.onDraw(canvas)


        log()
    }

    fun log() {
        println(sb.toString())
        sb = StringBuilder("==========================\n\n\n")
    }
}