package com.lanayru.view

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 *
 * Simple grid layout
 *
 * @author seven
 * @since 2018/8/8
 * @version V1.0
 */
class GridLayout(context: Context, c: Int) : ViewGroup(context) {

    private val column = c

    var horizontalMargin = 0
    var verticalMargin = 0

    private fun rowCount() = when {
        childCount == 0 -> 0
        childCount <= column -> 1
        else -> Math.ceil(childCount.toDouble() / column).toInt()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val count = childCount
        if (count <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        var maxItemWidth = 0
        var maxItemHeight = 0

        for (i in 0 until count) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            maxItemWidth = Math.max(maxItemWidth, child.measuredWidth)
            maxItemHeight = Math.max(maxItemHeight, child.measuredHeight)
        }

        val w = maxItemWidth * column + (column - 1) * horizontalMargin + paddingLeft + paddingRight
        val row = rowCount()
        val h = maxItemHeight * row + (row - 1) * verticalMargin + paddingTop + paddingBottom

        setMeasuredDimension(View.resolveSize(w, widthMeasureSpec), View.resolveSize(h, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val count = childCount
        val pl = paddingLeft
        val pr = paddingRight

        var l = pl
        var t = pr
        var r: Int
        var b = 0

        val row = rowCount()
        for (i in 0 until row) {
            for (j in 0 until column) {
                val index = i * column + j
                if (index >= count) {
                    return
                }
                val child = getChildAt(index)

                r = l + child.measuredWidth
                b = t + child.measuredHeight

                child.layout(l, t, r, b)

                l = r + horizontalMargin
            }

            l = pl
            t = b + verticalMargin
        }
    }
}