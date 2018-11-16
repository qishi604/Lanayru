package com.lanayru.app.ui.jni

import android.graphics.Bitmap

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/26
 *
 **/
object Bitmaps {

    init {
        System.loadLibrary("bitmap-lib")
    }

    external fun toRed(src: Bitmap, dst: Bitmap)

    external fun toGray(src: Bitmap, dst: Bitmap)

    fun gray(src: Bitmap): Bitmap {
        val w = src.width
        val h = src.height

        val pixs = IntArray(w * h)
        val dstpixs = IntArray(w * h)
        src.getPixels(pixs, 0, w, 0, 0, w, h)

        val alpha = 0xFF000000

        for (c in 0 until h) {
            for (r in 0 until w) {
                val index = w * c + r

                var color = pixs[index]

                val red = color and 0x00FF0000 shr 16
                val green = color and 0x0000FF00 shr 8
                val blue = color and 0x000000FF

                color = (red + green + blue) / 3

                color = (alpha or ((color shl 16).toLong()) or ((color shl 8).toLong()) or color.toLong()).toInt()

                dstpixs[index] = color
            }
        }

        val result = Bitmap.createBitmap(w, h, src.config)
        result.setPixels(dstpixs, 0, w, 0, 0, w, h)
        return result
    }

    fun gray1(src: Bitmap): Bitmap {
        val w = src.width
        val h = src.height

        val pixs = IntArray(w * h)
        val dstpixs = IntArray(w * h)
        src.getPixels(pixs, 0, w, 0, 0, w, h)

        val alpha = 0xFF000000

        for (c in 0 until h) {
            for (r in 0 until w) {
                val index = w * c + r

                var color = pixs[index]

                val red = color and 0x00FF0000 shr 16 * 299/1000
                val green = color and 0x0000FF00 shr 8 * 587/1000
                val blue = color and 0x000000FF * 114/1000

//                color = (red + green + blue) / 3

                color = (alpha or ((red shl 16).toLong()) or ((green shl 8).toLong()) or blue.toLong()).toInt()

                dstpixs[index] = color
            }
        }

        val result = Bitmap.createBitmap(w, h, src.config)
        result.setPixels(dstpixs, 0, w, 0, 0, w, h)
        return result
    }
}