package com.lanayru.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import com.lanayru.extention.dp
import com.lanayru.extention.screenHeight
import com.lanayru.extention.screenWidth
import com.lanayru.theme.Skin

/**
 *
 * @author seven
 * @since 2018/7/25
 * @version V1.0
 */
class ThemeItemView(context: Context): View(context), IDateView<Skin> {
    override var data: Skin? = null
        set(value) {
            field = value

            value?.run {
                statusPaint.color = colorPrimaryDark
                toolbarPaint.color = colorPrimary
                accentPaint.color = colorAccent
            }

            invalidate()
        }

    private val statusRect = Rect()
    private val toolbarRect = Rect()
    private val accentRect = RectF()

    private val statusPaint = Paint().apply {
        isAntiAlias = true
    }
    private val toolbarPaint = Paint().apply {
        isAntiAlias = true
    }
    private val accentPaint = Paint().apply {
        isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY-> {
                widthSize
            }

            MeasureSpec.UNSPECIFIED, MeasureSpec.AT_MOST -> {
                suggestedMinimumWidth
            }
            else -> {
                widthSize
            }
        }

        val height = width * screenHeight / screenWidth

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w <= 0) {
            return
        }

        val scale = w / screenWidth.toFloat()
        fun s(i: Int) = (scale * i).dp

        val statusHeight = s(24)
        statusRect.set(0, 0, w, statusHeight)

        val size = s(48)
        toolbarRect.set(0, statusHeight, w, statusHeight + size)

        val right = w - s(24)
        val bottom = h - s(24)
        accentRect.set((right - size).toFloat(), (bottom - size).toFloat(),right.toFloat(), bottom.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            drawColor(0xFFefefef.toInt())
            drawRect(statusRect, statusPaint)
            drawRect(toolbarRect, toolbarPaint)

            drawOval(accentRect, accentPaint)
        }
    }

}