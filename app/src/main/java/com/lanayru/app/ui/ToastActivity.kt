package com.lanayru.app.ui

import android.os.Bundle
import android.view.Gravity
import com.blankj.utilcode.util.ToastUtils
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * @author 郑齐
 * @since 2019-07-08
 * @version V1.0
 *
 */
class ToastActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            button("custom").onClick {
                showCustom()
            }
        }
    }

    private fun showCustom() {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0)
        ToastUtils.showCustomShort(R.layout.toast_custom)
    }


}