package com.lanayru.app.ui.keyboard

import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.view.Keyboard

/**
 * @author 郑齐
 * @since 2019/6/3
 * @version V1.0
 *
 */
class KeyboardActivity: BaseActivity() {

    override fun renderToolbar() {
//        super.renderToolbar()
    }

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_keyboard)

        var keyboard = Keyboard()
        keyboard.assistActivity(_this, findViewById(R.id.keyboard))
    }
}