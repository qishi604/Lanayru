package com.lanayru.app.ui.anim

import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_anim.*

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/12
 *
 **/
class AnimActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_anim)

        v1.setOnClickListener {
            show()
        }

        v2.setOnClickListener {
            hide()
        }

        v2.post {
            v2.translationX = v2.width.toFloat()
        }
    }

    private fun show() {
        v1.animate().translationX(-v1.width.toFloat()).setDuration(300).start()
        v2.animate().translationX(0f).start()
    }

    private fun hide() {
        v1.animate().translationX(0f).setDuration(300).start()
        v2.animate().translationX(v1.width.toFloat()).setDuration(300).start()
    }
}