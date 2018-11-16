package com.lanayru.app.ui.custom

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.widget.SeekBar
import com.kibey.echo.ui.widget.BluetoothBatteryView
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.util.invokeMethod
import kotlinx.android.synthetic.main.activity_custom_test.*

/**
 *
 * @author seven
 * @since 2018/7/18
 * @version V1.0
 */
class TestActivity : BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_custom_test)

        tv_title.text = _this.javaClass.name

        l_root.addView(BluetoothBatteryView(_this), 0)

        seek1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tv_seek1.text = "$progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        renderBattery()
    }

    private fun renderBattery() {
        val bt = BluetoothAdapter.getDefaultAdapter()
        bt.bondedDevices.forEach {
            getBattery(it)
        }
    }

    private fun getBattery(d: BluetoothDevice) {
        val level = invokeMethod(d, "getBatteryLevel")
        print(level)
    }
}