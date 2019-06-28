package com.lanayru.app.ui.anim

import android.os.Bundle
import android.os.Handler
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_record_wave.*

class RecordViewWaveActivity : BaseActivity() {

//    lateinit var mInterval: Disposable

    private val handler = Handler()

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_record_wave)

        btn_start.setOnClickListener { update() }
    }

    private val callback = {
        update()
    }

    private fun update() {
//        mInterval = Observable.interval(1, TimeUnit.SECONDS).subscribe {
//            random()
//        }

        handler.removeCallbacks(callback)
        random()
        handler.postDelayed(callback, 400)
    }

    private fun random() {
        var d = Math.random() * 100
        wave.setBase(d.toInt())
    }

    override fun onDestroy() {
//        mInterval.dispose()
        handler.removeCallbacks(callback)
        super.onDestroy()
    }

}