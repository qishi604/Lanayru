package com.lanayru.app.ui.bluetooth

import android.bluetooth.*
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.util.invokeMethod
import kotlinx.android.synthetic.main.activity_bluetooth_device.*
import org.jetbrains.anko.toast

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/11/8
 *
 **/
class BluetoothDeviceActivity: BaseActivity() {

    var address = "04:5D:4B:97:C1:3E" // 1000x

    val mBtAdapter = BluetoothAdapter.getDefaultAdapter()

    private var mHeadset: BluetoothHeadset? = null

    private lateinit var mTvTitle: TextView

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_bluetooth_device)

        mTvTitle = findViewById(R.id.tv_title)

        findViewById<View>(R.id.v_connect).setOnClickListener {
            connect()
        }

        findViewById<View>(R.id.v_disconnect).setOnClickListener {
            disconnect()
        }

        findViewById<View>(R.id.v_bond).setOnClickListener {
            bond()
        }

        findViewById<View>(R.id.v_removeBond).setOnClickListener {
            removeBond()
        }

        btn_set_name.setOnClickListener {
            setNmae()
        }

        initialize()
    }

    override fun onResume() {
        super.onResume()
        renderConnection()
    }

    private fun initialize() {
        if (mBtAdapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothProfile.STATE_CONNECTED) {
            mBtAdapter.bondedDevices.forEach {
//                if (it.name.endsWith("1000x", true)) {
//                    address = it.address
//                }

                if (isDeviceConnected(it)) {
                    address = it.address
                }
            }
        }


        val l = object : BluetoothProfile.ServiceListener {
            override fun onServiceDisconnected(profile: Int) {
            }

            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                mHeadset = proxy as BluetoothHeadset

            }
        }

        mBtAdapter.getProfileProxy(_this, l, BluetoothProfile.HEADSET)
    }

    private fun connect() {
        val device = mBtAdapter.getRemoteDevice(address) ?: return
        if (device.bluetoothClass.majorDeviceClass != BluetoothClass.Device.Major.AUDIO_VIDEO) {
            return
        }

        mHeadset?.let {
            invokeMethod(it, "connect", device)
        }
    }

    private fun disconnect() {
        val device = mBtAdapter.getRemoteDevice(address) ?: return
        if (device.bluetoothClass.majorDeviceClass != BluetoothClass.Device.Major.AUDIO_VIDEO) {
            return
        }

        mHeadset?.let {
            invokeMethod(it, "disconnect", device)
        }
    }


    private fun bond() {
        val device = mBtAdapter.getRemoteDevice(address) ?: return

        device.createBond()
    }

    private fun removeBond() {
        val device = mBtAdapter.getRemoteDevice(address) ?: return

        invokeMethod(device, "removeBond")
    }

    private fun renderConnection() {
        val device = mBtAdapter.getRemoteDevice(address) ?: return

        mTvTitle.text = if (isDeviceConnected()) "已连接 ${device.name} $address" else "未连接"

        invokeMethod(device, "getBatteryLevel")
    }

    private fun setNmae() {
        val name = et_name.text.toString()
        if (name.isEmpty()) {
            return
        }

        val device = getDevice()?: return

        val r = invokeMethod(device, "setAlias", name)
        if (r == true) {

            toast("change success")

        }
    }

    /**
     * 判断蓝牙设备是否连接
     */
    private fun isDeviceConnected(): Boolean {
        if (null == address) {
            return false
        }
        val device = getDevice() ?: return false
        return isDeviceConnected(device)
    }

    /**
     * 判断蓝牙设备是否连接
     */
    private fun isDeviceConnected(device: BluetoothDevice): Boolean {
        val r = invokeMethod(device, "isConnected")
        if (r is Boolean) {
            return r
        }

        return false
    }

    private fun getDevice() = mBtAdapter.getRemoteDevice(address) ?: null
}