package com.lanayru.util

import android.graphics.Bitmap

/**
 * @author seven
 * @since 2019/2/20
 * @version V1.0
 *
 */
object LeakBitmapList {

    val mList = ArrayList<Bitmap>()

    fun add(o: Bitmap) {
        mList.add(o)
    }
}