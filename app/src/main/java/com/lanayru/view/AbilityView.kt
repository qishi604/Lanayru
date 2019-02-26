package com.lanayru.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.lanayru.extention.dp

class AbilityView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var mRadius: Float = 0f

    var mInnerRadius: Float = 0f

    var mLineWidth: Float = 2.dp.toFloat()

    var mLinePaint: Paint

    var mFillPaint: Paint

    var mTextPaint: Paint

    var mContentPaint: Paint

    var mAbilityMark = arrayOf(100, 100, 60, 100, 90, 80)

    var AB = arrayOf("E", "D", "C", "B", "A")

    var mAbilityText = arrayOf("破坏力", "速度", "射程距离", "持久力", "精密度", "成长性")

    /**
     * 旋转角度
     */
    private var mDegree = 0f

    private var mTouchSlop: Int

    init {
        mLinePaint = Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            strokeWidth = mLineWidth
            style = Paint.Style.STROKE
        }

        mFillPaint = Paint().apply {
            color = Color.BLACK
            isAntiAlias = true
            style = Paint.Style.FILL
            strokeWidth = 4.dp.toFloat()
        }

        mTextPaint = Paint().apply {
            isAntiAlias = true
            color = Color.BLACK
            textAlign = Paint.Align.CENTER
        }

        mContentPaint = Paint().apply {
            color = 0x800000ff.toInt()
            isAntiAlias = true
            style = Paint.Style.FILL
        }

        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // 减法是为了做边界处理
        mRadius = (Math.min(measuredWidth, measuredHeight) - mLineWidth) / 2.0f
        mInnerRadius = mRadius * 0.6f
        mTextPaint.textSize = mRadius * 0.1f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (null == canvas) {
            return
        }

        // 将圆心移到(0,0)位置
        canvas.translate(measuredWidth / 2f, measuredHeight / 2f)

        if (mDegree != 0f) {
            canvas.rotate(mDegree)
        }

        drawCircle(canvas)
        drawInnerCircle(canvas)
        drawText(canvas)
        drawAbility(canvas)
    }

    private fun drawFrame(canvas: Canvas, f: () -> Unit) {
        canvas.save()
        f()
        canvas.restore()
    }

    // 画外圈
    private fun drawCircle(canvas: Canvas) {
        // 外圈
        canvas.drawCircle(0f, 0f, mRadius, mLinePaint)

        // 小黑条宽高
        val lineWidth = mRadius * 0.05f
        val lineHeight = mRadius * 0.08f

        // 内圈
        val r2 = mRadius - lineHeight
        canvas.drawCircle(0f, 0f, r2, mLinePaint)

        // 画小黑条
        val size = 22
        for (i in 0 until size) {
            val degree = 360f / size
            drawFrame(canvas) {
                canvas.rotate(i * degree)
                // 用圆点计算x,y坐标, 画矩形实现
//                val x = (cx - lineWidth) / 2
//                val y = cy - mRadius
//                val rect = RectF(x,y,x + lineWidth,y + lineHeight)
//                canvas.drawRect(rect, mFillPaint)

                canvas.drawLine(0f, -mRadius, 0f, -r2, mFillPaint)
            }
        }
    }

    /**
     * 画内圈
     */
    private fun drawInnerCircle(canvas: Canvas) {
        val r = mInnerRadius
        canvas.drawCircle(0f, 0f, r, mLinePaint)

        // 画线
        val path = Path()
        val size = 6
        for (i in 0 until size) {
            val degree = 360f / size
            drawFrame(canvas) {
                canvas.rotate(i * degree)

                path.moveTo(0f, 0f) // 将(0,0)作为起点
                path.lineTo(0f, -r) // (0,0)->(0,-r)画一条直线

                // 画5条小线
                val w = mRadius * 0.02f // 小线长度
                val N = 5
                for (j in 1..N) {
                    path.moveTo(-w, -r / 6 * j) // 新起一点
                    path.rLineTo(w * 2, 0f) // rLineTo 相对上一个点做一个偏移，如(0,1)rLineTo(1,1)=(0,1)lineTo(1,2)
                }

                canvas.drawPath(path, mLinePaint)
            }
        }
    }

    /**
     * 画文字
     */
    private fun drawText(canvas: Canvas) {
        val size = mAbilityText.size
        val r2 = mRadius - mRadius * 0.08f
        val y = -(r2 - mRadius * 0.06f - mTextPaint.textSize)
        val y2 = -(mInnerRadius + mRadius * 0.02f)
        mAbilityText.forEachIndexed { i, s ->
            val degree = 360f / size
            drawFrame(canvas) {
                canvas.rotate(i * degree)
                val array = FloatArray(1) { 0f }
                mTextPaint.breakText(s, false, measuredWidth.toFloat(), array)

                val x = -(array[0] / 2f)
                canvas.drawText(s, 0f, y, mTextPaint)

                val s2 = getA(mAbilityMark[i])
                mTextPaint.breakText(s2, false, measuredWidth.toFloat(), array)
                val x2 = -(array[0] / 2f)
                canvas.drawText(s2, 0f, y2, mTextPaint)
            }
        }
    }

    /**
     * 画形状
     */
    private fun drawAbility(canvas: Canvas) {
        val step = mInnerRadius / 6
        val path = Path()
        val N = mAbilityMark.size
        path.moveTo(0f, -mAbilityMark[0] / 20f * step) // 起点
        for (i in 1 until N) {
            val mark = mAbilityMark[i] / 20f
            val x = mark * step * Math.cos(Math.PI / 180 * (-30 + 60 * (i - 1))).toFloat()
            val y = mark * step * Math.sin(Math.PI / 180 * (-30 + 60 * (i - 1))).toFloat()
            path.lineTo(x, y)
        }
        path.close()
        canvas.drawPath(path, mContentPaint)
    }

    private fun getA(v: Int): String {
        var tmp = when {
            v > 100 -> 100
            v < 0 -> 10
            else -> v
        }
        val index = tmp / 20 - 1
        return AB[index]
    }


    private var x0 = 0f
    private var y0 = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (null == event) {
            return super.onTouchEvent(event)
        }

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                x0 = rX(event.x)
                y0 = rY(event.y)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val x = rX(event.x)
                val y = rY(event.y)
                val dx = x - x0
                val dy = y - y0

                if (dx < mTouchSlop && dy < mTouchSlop) {
                    return super.onTouchEvent(event)
                }

                // 计算两条直线的夹角
                val a = y / x
                val b = y0 / x0
                val c = (Math.abs(a-b)/(1+a*b)).toDouble()
                val degree = (Math.atan(c) / Math.PI * 180).toFloat()

//                val isNegative = dx < 0
//
//                val dis = Math.sqrt((dx * dx + dy * dy).toDouble())
//
//                val degree = (dis / mRadius * Math.PI * 180).toFloat()
                mDegree += degree
                x0 = x
                y0 = y
                invalidate()
            }

            MotionEvent.ACTION_UP -> {

            }

        }

        return super.onTouchEvent(event)
    }

    // 因为canvas移动的缘故，需要对触摸点做一个偏移
    private fun rX(v: Float) = v - measuredWidth / 2f
    private fun rY(v: Float) = v - measuredHeight / 2f
}