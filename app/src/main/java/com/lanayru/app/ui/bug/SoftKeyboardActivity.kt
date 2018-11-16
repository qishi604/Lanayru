package com.lanayru.app.ui.bug

import android.os.Bundle
import android.view.WindowManager
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.view.AndroidBug5497Workaround

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
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_soft_keyboard)

        AndroidBug5497Workaround.assistActivity(this)
    }
}