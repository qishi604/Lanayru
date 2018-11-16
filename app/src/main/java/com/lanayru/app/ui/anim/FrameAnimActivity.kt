package com.lanayru.app.ui.anim

import android.os.Bundle
import android.view.ViewGroup
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.view.FrameAnimView
import org.jetbrains.anko.verticalLayout

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/11
 *
 **/
class FrameAnimActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        val root = verticalLayout {
        }
        root.addView(FrameAnimView(_this).apply {
            load(R.drawable.f_gif)
        }, ViewGroup.LayoutParams(42.dp, 42.dp))

        setContentView(root)
    }
}