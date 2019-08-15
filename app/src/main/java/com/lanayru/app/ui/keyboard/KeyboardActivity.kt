package com.lanayru.app.ui.keyboard

import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.app.ui.bug.SoftKeyboardActivity
import com.lanayru.view.Keyboard
import org.jetbrains.anko.startActivity

/**
 *
 * ### 这个Activity 测试几个问题
 *
 * - 键盘显示隐藏、切换
 * - 测试监听 addOnGlobalLayoutListener，当 Activity在后台时，是否会监听到事件。结论是***不会***
 *
 * @author 郑齐
 * @since 2019/6/3
 * @version V1.0
 *
 */
class KeyboardActivity: BaseActivity() {

    private var mKeyboard: Keyboard? = null


    override fun renderToolbar() {
        super.renderToolbar()

        _toolbar!!.setOnClickListener {
            startActivity<SoftKeyboardActivity>()
        }
    }

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_keyboard)

        mKeyboard = Keyboard()
        mKeyboard!!.assistActivity(_this, findViewById(R.id.keyboard))
    }

    override fun onBackPressed() {
        if (null != mKeyboard && mKeyboard!!.hidePanel()) {
            return
        }

        super.onBackPressed()
    }
}