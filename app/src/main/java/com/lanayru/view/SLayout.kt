package com.lanayru.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout




class SLayout: FrameLayout {

    val mPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
        xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        style = Paint.Style.FILL
    }

    val mPath = Path()

    val mRectF = RectF()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.width
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF.left = getPaddingLeft().toFloat()
        mRectF.top = getPaddingTop().toFloat()
        mRectF.right = w - getPaddingRight().toFloat()
        mRectF.bottom = h - getPaddingBottom().toFloat()
        val r= h / 2f
        mPath.addRoundRect(mRectF, r, r, Path.Direction.CW)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        if (null == canvas) {
            return
        }

        canvas.saveLayer(RectF(0f, 0f, canvas.getWidth().toFloat(), canvas.getHeight().toFloat()), null, Canvas
                .ALL_SAVE_FLAG)
        // 绘制子控件
        super.dispatchDraw(canvas)
        // 绘制带有圆角的 Path
        canvas.drawPath(mPath, mPaint)
        canvas.restore()

    }

    private fun clip(canvas: Canvas) {
        val rect = RectF(0.toFloat(),0.toFloat(),width.toFloat(), height.toFloat())
        val r = height / 2.toFloat()
        val path = Path()
        path.addRoundRect(rect, r, r, Path.Direction.CCW)
        canvas.clipPath(path)



//        val finalBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        val mCanvas = Canvas(finalBitmap)
//        mCanvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)// 画布绘制Bitmap时搞锯齿
////        mCanvas.drawPath(path, mPaint)
//
//        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
//        mCanvas.clipPath(path)
//        canvas.drawBitmap(finalBitmap, 0f, 0f, mPaint)
    }
}