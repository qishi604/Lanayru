package com.lanayru.app.ui.custom

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.view.WaveView

class WaveViewActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        addWaveView()
    }

    private fun addWaveView() {
        val waveView = WaveView(_this).apply {
            setBackgroundColor(Color.RED)
            setInitialRadius(4.dp.toFloat())
            setColor(Color.GREEN)
            setStyle(Paint.Style.FILL)
        }

        waveView.setOnClickListener {
            if (waveView.isRunning) {
                waveView.stop()

            } else {
                waveView.start()
            }
        }

        setContentView(waveView)
    }
}