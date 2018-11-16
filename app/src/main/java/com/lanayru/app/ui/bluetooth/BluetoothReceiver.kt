package com.lanayru.app.ui.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.lanayru.util.Logs

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/17
 *
 **/
class BluetoothReceiver: BroadcastReceiver() {

    companion object {
        const val ACTION_DISAPPEARED = "android.bluetooth.device.action.DISAPPEARED"
    }

    var listener : BluetoothReceiverListener? = null

    private val mFilter = IntentFilter().apply {
        addAction(BluetoothDevice.ACTION_FOUND)
        addAction(ACTION_DISAPPEARED)
        addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
        addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        addAction(BluetoothAdapter.ACTION_STATE_CHANGED)

        addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
    }

    fun register(context: Context) {
        context.registerReceiver(this, mFilter)
    }

    fun unRegister(context: Context) {
        context.unregisterReceiver(this)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (null == intent) {
            return
        }

        val action = intent.action
        Logs.i(action)
        if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
            listener?.onStateChange(intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0))
            return
        }

        val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE) ?: return

        when (intent.action) {
            BluetoothDevice.ACTION_FOUND -> {
                listener?.onFound(device)
            }

            ACTION_DISAPPEARED -> {
                listener?.onDisappeared(device)
            }

            BluetoothDevice.ACTION_PAIRING_REQUEST -> {
                listener?.onPairing(device)
            }

            BluetoothDevice.ACTION_BOND_STATE_CHANGED -> {
                listener?.onBondChange(device)
            }
        }
    }

    interface BluetoothReceiverListener {

        /**
         * Bluetooth state change (STATE_OFF)[BluetoothAdapter.STATE_ON]}
         *
         */
        fun onStateChange(state: Int)

        /**
         *  find bluetooth device
         */
        fun onFound(device: BluetoothDevice)
        /**
         *  bluetooth device disappeared
         */
        fun onDisappeared(device: BluetoothDevice)

        fun onPairing(device: BluetoothDevice)

        fun onBondChange(device: BluetoothDevice)
    }
}