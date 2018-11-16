package com.kibey.echo.ui.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v7.widget.AppCompatTextView
import android.text.TextPaint
import android.util.AttributeSet

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/25
 *
 **/
class BluetoothBatteryView: AppCompatTextView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val COLOR_GREEN = 0xff6ED56C.toInt()
    private val COLOR_GRAY = 0xffC3C3C3.toInt()
    private val mOffset = 1.dp
    private val mBOffset = 2.dp
    private val mTextLeft = 31.dp

    private val _1DP = 1.dp

    private val _05DP = 0.5f.dp

    private val mBw = 18.dp

    private val mBh = 8.dp

    private val mBatteryRect: RectF

    private val mStrokeRect:RectF

    private val mDotRect:RectF

    private val mBatteryPaint: Paint

    private val mStrokePaint:Paint

    private val mDotPaint:Paint

    private val mTextPaint: TextPaint

    private var mLevel: Int = 100

    private var mBatteryStr = "100%"

    private var mTX = 0f

    private var mTY = 0f

    private fun newPaint() = Paint().apply {
        isAntiAlias = true
    }

    init {
        // init paint
        mBatteryPaint = newPaint().apply {
            color = COLOR_GREEN
        }

        mStrokePaint = newPaint().apply {
            color = COLOR_GRAY
            style = Paint.Style.STROKE
            strokeWidth = _1DP
        }

        mDotPaint = newPaint().apply {
            color = COLOR_GRAY
        }

        mBatteryRect = RectF(0f, 0f, mBw, mBh)

        // init rect
        val r = 20.dp
        mStrokeRect = RectF(0f, 0f, r, 10.dp)

        val l = mStrokeRect.right +_1DP
        val dotH = 4.dp
        val t = (mStrokeRect.bottom - dotH) / 2
        mDotRect = RectF(l, t, l + 1.dp, t + dotH)

        mTX = 0f
        mTY = mStrokeRect.bottom
        mTextPaint = TextPaint().apply {
            color = COLOR_GREEN
            isAntiAlias = true
            textSize = 12.dp
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widths = FloatArray(1)
        mTextPaint.breakText("100%", false, 120.dp, widths)
        val w = (mTextLeft + widths[0]).toInt()
        val h = 14.dp.toInt()
        setMeasuredDimension(w, h)
    }

    private fun drawBatteryText(canvas: Canvas) {
        drawSomething(canvas) {
            canvas.translate(mTextLeft, 0f)
            canvas.drawText(mBatteryStr, mTX, mTY, mTextPaint)
        }
    }


    private val mBatteryR = 0.5f.dp

    /**
     * 画电量
     * @param canvas Canvas
     */
    private fun drawBattery(canvas: Canvas) {
        if (mLevel <= 0) {
            return
        }

        mBatteryRect.right = mLevel * mBw / 100f
        drawSomething(canvas) {
            canvas.translate(mBOffset, mBOffset)

            canvas.drawRoundRect(mBatteryRect, mBatteryR, mBatteryR,  mBatteryPaint)
        }
    }

    private fun drawBatteryStroke(canvas: Canvas) {

        drawSomething(canvas) {
            canvas.translate(mOffset, mOffset)

            // 画电池边框
            val r = _1DP
            canvas.drawRoundRect(mStrokeRect, r, r, mStrokePaint)

            // 画电池的头
            val rectf = RectF(mDotRect)
            rectf.right = rectf.right - _05DP
            canvas.drawRect(rectf, mDotPaint)

            canvas.drawRoundRect(mDotRect, r, r, mDotPaint)

        }
    }

    override fun onDraw(canvas: Canvas) {
        drawBatteryText(canvas)

        drawBatteryStroke(canvas)
        drawBattery(canvas)
    }

    private fun drawSomething(canvas: Canvas, c: () -> Unit) {
        canvas.save()
        c()
        canvas.restore()
    }

    /**
     * 设置电量
     * @param level 电量 [0-100]
     */
    private fun setBattery(level: Int) {
        mLevel = level
        mBatteryStr = "$level%"
        invalidate()
    }


    val Int.dp :Float get() = Resources.getSystem().displayMetrics.density * this

    val Float.dp :Float get() = Resources.getSystem().displayMetrics.density * this
}