package com.lanayru.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.lanayru.extention.dp

/**
 *
 *
 * A Rule View
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/19
 *
 **/
class RulerView: View {

    private val mPaint: Paint = Paint().let {
        it.color = Color.RED
        it.isAntiAlias = true
        it.strokeWidth = 1f

        it
    }

    private val mTextPaint: Paint = Paint().let {
        it.color = Color.RED
        it.isAntiAlias = true
        it.strokeWidth = 1f
        it.textSize = 12.dp.toFloat()

        it
    }

    private val mBluePaint = Paint(mPaint).let {
        it.color = Color.BLUE
        it
    }

    private val mBlueTextPaint = Paint(mTextPaint).let {
        it.color = Color.BLUE
        it
    }

    private var mH: Float = 0f

    private val sH = 18.dp

    private val hH = 2*sH

    private val lH = 3*sH

    /**
     * mm pixels
     */
    private var mm: Float = 0f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {

        resources.displayMetrics.let {
            mm = it.ydpi / 2.54f / 10
            mH = it.heightPixels /  it.ydpi // screen height inch
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawHMM(it)
            drawHMM2(it)
        }
    }

    private fun drawSomething(canvas: Canvas, c: () -> Unit) {
        canvas.save()
        c()
        canvas.restore()
    }

    private fun drawHMM(canvas: Canvas) {
        drawSomething(canvas) {
            canvas.translate(measuredWidth.toFloat(), 0f)
            canvas.rotate(90f)

            drawMM(canvas)
        }
    }

    private fun drawHMM2(canvas: Canvas) {
        drawSomething(canvas) {
            canvas.translate(0f, measuredHeight.toFloat())
            canvas.rotate(-90f)

            drawMM(canvas, mBluePaint, mBlueTextPaint)
        }
    }

    private fun drawMM(canvas: Canvas, linePaint: Paint = mPaint, textPaint: Paint = mTextPaint) {
        val len = (measuredHeight / mm).toInt()
        for (i in 0 until len) {
            val isCM = i % 10 == 0

            val h = when {
                isCM -> lH
                i % 5 == 0 -> hH
                else -> sH
            }

            val sx = mm * i
            canvas.drawLine(sx, 0f, sx, h.toFloat(), linePaint)

            // draw text
            if (isCM) {
                val text = "${i / 10}"
                var tx = if (sx > 12) sx - 4.dp else sx
                var ty = (h + 18.dp).toFloat()
                canvas.drawText(text, tx, ty, textPaint)
            }
        }
    }

//    private fun drawVMM(canvas: Canvas) {
//        val len = (measuredHeight / mm).toInt()
//        val _w = measuredWidth
//        for (i in 0 until len) {
//            val isCM = i % 10 == 0
//
//            val h = if (isCM) {
//                lH
//            } else if (i % 5 == 0) {
//                hH
//            } else {
//                sH
//            }
//
//            val sx = (_w - h).toFloat()
//            val sy = mm * i
//            canvas.drawLine(sx, sy, _w.toFloat(), sy, mPaint)
//
//            if (isCM) {
//                val text = "${i / 10}"
//                val tx =  sx - 20.dp
//                var ty = sy + 6.dp
//                ty = if (ty < 12.dp) 12.dp.toFloat() else ty
//                canvas.drawText(text, tx, ty, mTextPaint)
//            }
//
//        }
//    }
}