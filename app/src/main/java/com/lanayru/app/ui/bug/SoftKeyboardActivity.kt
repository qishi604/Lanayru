package com.lanayru.app.ui.bug

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.KeyboardUtils
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.view.AndroidBug5497Workaround
import kotlinx.android.synthetic.main.layout_keyboard.*

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/16
 *
 **/
class SoftKeyboardActivity: BaseActivity() {

    override fun renderToolbar() {
//        super.renderToolbar()
    }

    override fun render(savedInstanceState: Bundle?) {
//        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_soft_keyboard)

        AndroidBug5497Workaround.assistActivity(this).apply {
            onShow = {
                l_add.visibility = View.GONE
                l_face.visibility = View.GONE
            }

            onHide = {

            }
        }

        iv_face.setOnClickListener {
            KeyboardUtils.hideSoftInput(_this)
            l_add.visibility = View.GONE
            l_face.visibility = View.VISIBLE
        }

        iv_add.setOnClickListener {
            KeyboardUtils.hideSoftInput(_this)
            l_face.visibility = View.GONE
            l_add.visibility = View.VISIBLE
        }
    }
}