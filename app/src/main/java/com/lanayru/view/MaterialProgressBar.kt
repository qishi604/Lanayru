package com.lanayru.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.widget.CircularProgressDrawable
import android.widget.ImageView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 *
 * Material ProgressBar
 *
 * @author seven
 * @since 2018/8/7
 * @version V1.0
 */
class MaterialProgressBar(context: Context): ImageView(context), AnkoLogger {

    private val mProgressDrawable = CircularProgressDrawable(context)

    init {
        setImageDrawable(mProgressDrawable)
    }

    fun setColors(vararg colors: Int) {
        mProgressDrawable.setColorSchemeColors(*colors)
    }

    fun setStrokeWidth(width: Int) {
        mProgressDrawable.strokeWidth = width.toFloat()
    }

    fun start() {
        mProgressDrawable.start()
    }

    fun stop() {
        if (mProgressDrawable.isRunning) {
            mProgressDrawable.stop()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        info("MProgressBar onDraw")
    }

    override fun invalidateDrawable(dr: Drawable?) {
        super.invalidateDrawable(dr)

    }
}