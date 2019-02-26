package com.lanayru.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.lanayru.extention.dp

/**
 * 演示[PathMeasure] 做Path 动画
 */
class PathAnimView: View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val mPath: Path = Path().apply {
        moveTo(0f,0f)
        lineTo(100.dp.toFloat(), 0f)
        rLineTo(0f, 200.dp.toFloat())
        rLineTo(-100.dp.toFloat(), 0f)
        close()
    }

    private val mPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        strokeWidth = 2.dp.toFloat()
        style = Paint.Style.STROKE
    }


    init {
        val pathMeasure = PathMeasure(mPath, true)
        val anim = ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 5000
            interpolator = DecelerateInterpolator()
        }
        anim.addUpdateListener {
            val v = it.animatedValue as Float

            val len = pathMeasure.length
            val array = floatArrayOf(len, len)
            val effect = DashPathEffect(array, v * len)
            mPaint.pathEffect = effect
            invalidate()
        }

        anim.start()

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (null == canvas) {
            return
        }

        canvas.translate(measuredWidth / 2f, measuredHeight/ 2f)
        canvas?.drawPath(mPath, mPaint)
    }
}