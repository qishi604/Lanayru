package com.lanayru.app.ui.bluetooth

import android.bluetooth.BluetoothAssignedNumbers
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothHeadset
import android.bluetooth.BluetoothProfile
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.util.Logs
import org.jetbrains.anko.toast

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/22
 *
 **/
class HeadsetInfoActivity: BluetoothActivity(), BluetoothProfile.ServiceListener {

    private var mHeadset: BluetoothHeadset? = null

    private val mEventReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (null == intent) {
                return
            }

            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE) ?: return
            onHeadsetEvent(device, intent)
        }
    }

    override fun onServiceDisconnected(profile: Int) {
    }

    override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
        if (profile != BluetoothProfile.HEADSET) {
            return
        }

        proxy?.let {
            val headset = it as BluetoothHeadset
            mHeadset = headset

            if (headset.connectedDevices.size > 0) {
                val device = headset.connectedDevices[0]
                device.type
            }

        }
    }

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_headset)

        registerReceiver()
        getHeadset()
    }

    private fun getHeadset() {
        val success = mBtAdapter.getProfileProxy(_this, this, BluetoothProfile.HEADSET)
        if (!success) {
            toast("Get headset fail!")
        }
    }

    private fun closeProfile() {
        mBtAdapter.closeProfileProxy(BluetoothProfile.HEADSET, mHeadset)
    }


    private fun onHeadsetEvent(device: BluetoothDevice, intent: Intent) {
        val args = intent.getSerializableExtra(BluetoothHeadset.EXTRA_VENDOR_SPECIFIC_HEADSET_EVENT_ARGS)
        Logs.i("args: $args")
    }

    private fun registerReceiver() {
        val filter = IntentFilter().apply {
            addAction(BluetoothHeadset.ACTION_VENDOR_SPECIFIC_HEADSET_EVENT)
            addCategory(BluetoothHeadset.VENDOR_SPECIFIC_HEADSET_EVENT_COMPANY_ID_CATEGORY + "." + BluetoothAssignedNumbers.CAMBRIDGE_SILICON_RADIO)
//            addCategory(BluetoothHeadset.VENDOR_SPECIFIC_HEADSET_EVENT_COMPANY_ID_CATEGORY + "." + BluetoothAssignedNumbers.APPLE)
        }
        registerReceiver(mEventReceiver, filter)
    }

    private fun unRegisterReceiver() {
        unregisterReceiver(mEventReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        closeProfile()
        unRegisterReceiver()
    }
}