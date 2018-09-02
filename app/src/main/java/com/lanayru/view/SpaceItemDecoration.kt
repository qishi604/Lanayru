package com.lanayru.view

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 *
 * @author seven
 * @since 2018/7/25
 * @version V1.0
 */
class SpaceItemDecoration(size: Int): RecyclerView.ItemDecoration() {

    val size = size

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        if (null == outRect || null == view || null == parent) {
            return
        }

        val lp = view.layoutParams as RecyclerView.LayoutParams

        val position = lp.viewLayoutPosition

        var l = size
        val t = 0
        var r = size

        val half = size/ 2

        val b = size

        val layoutManager = parent.layoutManager

        if (layoutManager is GridLayoutManager) {
            val lookup = layoutManager.spanSizeLookup

            val spanCount = layoutManager.spanCount

            val spanSize = lookup.getSpanSize(position)

            if (spanSize != spanCount) {

                val index = lookup.getSpanIndex(position, spanCount)

                when (index) {
                    0 -> r = half

                    spanCount - 1 -> l = half

                    else -> {
                        l = half
                        r = half
                    }
                }
            }
        }

        outRect.set(l, t, r, b)
    }
}