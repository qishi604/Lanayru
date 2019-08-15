package com.lanayru.app.ui.custom

import android.os.Bundle
import android.view.Choreographer
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.util.Logs

class AbilityViewActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_ability_view)

        val choreographer = Choreographer.getInstance()

        val callback = object : Choreographer.FrameCallback {

            override fun doFrame(frameTimeNanos: Long) {
                Logs.i("doframe ===================== ")
                Logs.i("do something")
                Thread.sleep(12)
                choreographer.postFrameCallback(this)
            }

        }

        Choreographer.getInstance().postFrameCallback(callback)
    }
}