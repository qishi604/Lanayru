package com.lanayru.app.ui

import android.os.Bundle
import android.view.View
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.view.Chronometer
import kotlinx.android.synthetic.main.activity_chrometer.*

/**
 * @author 郑齐
 * @since 2019/6/10
 * @version V1.0
 *
 */
class ChronometerActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_chrometer)

        initChronometer()

        btn_start.setOnClickListener {
            start()
        }

        btn_stop.setOnClickListener {
            stop()
        }
    }

    private fun initChronometer() {
        chronometer.setMillisInFuture(60 * 1000)
        chronometer.setOnTickListener(object : Chronometer.OnTickListener{
            override fun onTick(ms: Long) {
                formatText(ms)
            }

            override fun onStop() {
                stop()
            }

        })
    }

    private fun start() {
        chronometer.visibility = View.VISIBLE
        chronometer.restart()
    }

    private fun stop() {
        chronometer.visibility = View.GONE
        chronometer.stop()

    }

    private fun formatText(ms: Long) {
        val second = ms / 1000
        if (second < 10) {
            chronometer.text = "0:0$second"
        } else {
            chronometer.text = "0:$second"
        }
    }
}