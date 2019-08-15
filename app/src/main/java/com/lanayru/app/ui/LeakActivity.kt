package com.lanayru.app.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.util.LeakBitmapList
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * 用 profiler 检测内存泄漏
 *
 * @author seven
 * @since 2019/2/20
 * @version V1.0
 *
 */
class LeakActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("add bitmap") {
                onClick {
                    addBitmap()
                }
            }
        }
    }

    private fun addBitmap() {
        val b = BitmapFactory.decodeResource(resources, R.drawable.img_d)
        LeakBitmapList.add(b)
    }
}