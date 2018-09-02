package com.lanayru.view

import android.content.Context
import android.graphics.*
import android.util.SparseArray
import android.view.View
import com.lanayru.app.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.collections.forEach
import org.jetbrains.anko.info

/**
 *
 * @author seven
 * @since 2018/7/18
 * @version V1.0
 */
class PullLoadingView(context: Context) : View(context), AnkoLogger {

    private val a = arrayOf(
            R.drawable.ic_pull_anim_1,
            R.drawable.ic_pull_anim_2,
            R.drawable.ic_pull_anim_3,
            R.drawable.ic_pull_anim_4,
            R.drawable.ic_pull_anim_5,
            R.drawable.ic_pull_anim_6,
            R.drawable.ic_pull_anim_7,
            R.drawable.ic_pull_anim_8,
            R.drawable.ic_pull_anim_9,
            R.drawable.ic_pull_anim_10,
            R.drawable.ic_pull_anim_11,
            R.drawable.ic_pull_anim_12,
            R.drawable.ic_pull_anim_13,
            R.drawable.ic_pull_anim_14
    )

    private val cache = SparseArray<Bitmap>(a.size)

    private val paint = Paint()

    private var i = 0

    private var reverse = false

    private val m = Matrix()

    private var bw = 0
    private var bh = 0

    init {
        var option = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, a[0], this)
        }
        bw = option.outWidth
        bh = option.outHeight

        val s = 2f
//        m.postScale(s, s)

//        m.postTranslate((2 * option.outWidth - bw) / 2.toFloat(), 0f)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        info("onMeasure w: $widthMeasureSpec h: $heightMeasureSpec")

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)


        val width = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize

        } else {
            val desiredWidth = Math.max(bw, suggestedMinimumWidth)

            if (widthMode == MeasureSpec.AT_MOST) {
                Math.min(widthSize, desiredWidth)
            } else {
                desiredWidth
            }
        }

        val height = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize

        } else {
            val desiredHeight = Math.max(bh, suggestedMinimumHeight)

            if (heightMode == MeasureSpec.AT_MOST) {
                Math.min(heightSize, desiredHeight)
            } else {
                desiredHeight
            }
        }

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        info("onSizeChanged $w $h $oldw $oldh")
    }

    private fun resolveAdjustedSize(measureSpec: Int): Int {
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)

        when (specMode) {
            MeasureSpec.UNSPECIFIED-> {
                // 默认值，未指定大小，可以设置为任意大小
                info("UNSPECIFIED size: $specSize")
            }

            MeasureSpec.AT_MOST-> {
                // 大小没有限制，但是存在上限。上限一般为父View 大小
                info("AT_MOST size: $specSize")
            }

            MeasureSpec.EXACTLY-> {
                // 已经确定大小
                info("EXACTLY size: $specSize")

            }
        }

        return 0
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        info("onLayout ==== left: $left top: $top right: $right bottom: $bottom")
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawColor(Color.parseColor("#33008800"))
            drawAnim(canvas)
        }
    }

    fun drawAnim(canvas: Canvas) {
        val bm = getBm()

        canvas.drawBitmap(bm, m, paint)
        postDelayed({
            invalidate()

        }, 50)
    }

    fun getBm(): Bitmap {
        var bm = cache.get(i)
        if (null == bm || bm.isRecycled) {
            bm = BitmapFactory.decodeResource(resources, a[i])
            cache.put(i, bm)
        }

        if (reverse) {
            i--
            if (i <= 0) {
                reverse = false
                i = 1
            }

        } else {
            i++
            if (i >= a.size) {
                reverse = true
                i = a.size - 2
            }
        }

        return bm
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        cache.forEach { _, bm -> bm.recycle() }
        cache.clear()
    }
}