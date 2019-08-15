package com.lanayru.app.ui.anim

import android.os.Bundle
import android.os.Handler
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_record_wave.*
import java.util.*

class RecordViewWaveActivity : BaseActivity() {

//    lateinit var mInterval: Disposable

    private val duration = 100L

    private val handler = Handler()

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_record_wave)

        btn_start.setOnClickListener { update() }

        wave.setAnimDuration(duration)
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
        handler.postDelayed(callback, duration)
    }

    val random  = Random()
    private fun random() {
        var d = random.nextInt(99) + 1
        wave.setBase(d)
    }

    override fun onDestroy() {
//        mInterval.dispose()
        handler.removeCallbacks(callback)
        super.onDestroy()
    }

}