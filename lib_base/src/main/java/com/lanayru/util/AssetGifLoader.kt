package com.lanayru.util

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * @author 郑齐
 * @since 2019-06-29
 * @version V1.0
 *
 */
object AssetGifLoader {

    fun load(iv: ImageView, name: String) {
        Glide.with(iv.context)
                .load(Uri.parse(getPath(name)))
                .into(iv)
    }

    fun getPath(name: String) = "file:///android_asset/gif/$name"
}