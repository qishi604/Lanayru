package com.lanayru.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.widget.CircularProgressDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.lanayru.extention.dp
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 *
 * ProgressDrawable 原理：在动画update invalidateSelf
 * 注意几个方法：Drawable.setCallback()、View.verifyDrawable()
 *
 * @author seven
 * @since 2018/8/2
 * @version V1.0
 */
class MusicProgressBar : View, AnkoLogger {

    var bg1: Int = Color.BLUE
    var bg2: Int = Color.RED
    var bg3: Int = Color.RED

    var bgH: Int = 4.dp

    var thumb: Drawable

    val bg1Paint: Paint
    val bg2Paint: Paint

    val bgRect = Rect()
    val bg1Rect = Rect()

    var progress: Int = 0
        set(value) {
            field = value
            invalidate()
        }

    private val progressDrawable: CircularProgressDrawable = CircularProgressDrawable(context)

    constructor(context: Context?) : super(context)

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        progressDrawable.strokeWidth = 2.dp.toFloat()

        progressDrawable.setColorSchemeColors(Color.CYAN)

        progressDrawable.callback = this

        bg1Paint = Paint().apply {
            isAntiAlias = true
            this.color = bg1
        }

        bg2Paint = Paint().apply {
            isAntiAlias = true
            this.color = bg2
        }


        val color = Color.RED
        thumb = createThumb(color, 24.dp)

        progress = 80

    }

    private fun createThumb(color: Int, size: Int): Drawable {
        progressDrawable.setBounds(0,0,size,size)

        val b = GradientDrawable().apply {
            setColor(color)
            shape = GradientDrawable.OVAL
            setSize(size, size)
        }

        val dotSize = 2.dp
        val l = (size - dotSize) / 2
        val dotDrawable = GradientDrawable().apply {
            setColor(Color.WHITE)
            shape = GradientDrawable.OVAL
            setStroke(l, Color.TRANSPARENT)
        }

        val tl = 0.dp
        return LayerDrawable(arrayOf(b, dotDrawable)).apply {
            setBounds(tl, 0, tl + size, size)
        }
    }

    private fun updateThumbPosition() {
        var r = progress * measuredWidth / 100
        if (r < thumb.intrinsicWidth) {
            r = thumb.intrinsicWidth
        } else if (r > measuredWidth) {
            r = measuredWidth
        }

        bg1Rect.right = r

        var l = r - thumb.intrinsicWidth

        thumb.setBounds(l, 0, r, thumb.intrinsicHeight)
        progressDrawable.setBounds(l, 0, r, thumb.intrinsicHeight)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val w = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize

            else -> {
                suggestedMinimumWidth
            }
        }

        val h = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize

            else -> {
                val thumbHeight = thumb?.intrinsicHeight ?: 0
                Math.max(thumbHeight, suggestedMinimumHeight)
            }
        }

        setMeasuredDimension(w, h)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bgRect.set(0, 0, w, bgH)
        bg1Rect.set(0, 0, 0, bgH)

        updateThumbPosition()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawProgress(canvas)
            drawThumb(canvas)
        }
    }

    private fun drawProgress(canvas: Canvas) {
        canvas.save()
        canvas.translate(0f, (measuredHeight - bgH) / 2f)
        canvas.drawRect(bgRect, bg1Paint)

        updateThumbPosition()

        canvas.drawRect(bg1Rect, bg2Paint)
        canvas.restore()
    }

    private fun drawThumb(canvas: Canvas) {
        thumb.draw(canvas)
        progressDrawable.draw(canvas)
        if (!progressDrawable.isRunning) {
            progressDrawable.start()
        }
    }

    override fun verifyDrawable(who: Drawable?): Boolean {
        return null != progressDrawable || super.verifyDrawable(who)
    }

    override fun invalidateDrawable(drawable: Drawable?) {
        super.invalidateDrawable(drawable)
        info { "invalidateDrawable ===============" }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (null == event || !isEnabled) {
            return false
        }

        when (event.action) {

            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val p = (x * 100 / measuredWidth).toInt()
                progress = p
            }

        }


        return true

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        progressDrawable.stop()
    }
}