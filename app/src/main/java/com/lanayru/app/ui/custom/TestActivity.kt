package com.lanayru.app.ui.custom

import android.os.Bundle
import android.view.Gravity
import com.lanayru.app.ui.base.BaseActivity
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

/**
 *
 * @author seven
 * @since 2018/7/18
 * @version V1.0
 */
class TestActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(
                verticalLayout {
                    textView(_this.javaClass.name) {

                    }.lparams {
                        gravity = Gravity.CENTER
                    }
                }
        )
    }
}