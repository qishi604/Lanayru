package com.lanayru.app.ui.anim

import android.os.Bundle
import android.os.Handler
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_record_wave.*

class RecordViewWaveActivity : BaseActivity() {

    val handler = Handler()

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_record_wave)

        btn_start.setOnClickListener { update() }
    }

    private val callback = {
        update()
    }

    private fun update() {
        handler.removeCallbacks(callback)

        var d = Math.random() * 100 + 20
        wave.setBase(d.toInt())

        handler.postDelayed(callback, 50)
    }

    override fun onDestroy() {
        handler.removeCallbacks(callback)
        super.onDestroy()
    }

}