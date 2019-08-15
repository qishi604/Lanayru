package com.lanayru.app.ui.bluetooth

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.util.Logs
import com.tbruyelle.rxpermissions2.RxPermissions
import org.jetbrains.anko.toast

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/17
 *
 **/
abstract class BluetoothActivity: BaseActivity(),  BluetoothReceiver.BluetoothReceiverListener {

    private val BluetoothAdapter.isDisabled: Boolean
        get() = !isEnabled

    companion object {
        private const val REQUEST_ENABLE_BT = 0x11

    }

    lateinit var mPermissions: RxPermissions

    lateinit var mBluetoothReceiver: BluetoothReceiver

    val mBtAdapter = BluetoothAdapter.getDefaultAdapter()

    val mReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            Logs.i(intent?.action?: "no action")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mPermissions = RxPermissions(_this)
        mBluetoothReceiver = BluetoothReceiver()

        mBluetoothReceiver.listener = this

        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mBluetoothReceiver.register(_this)
        requestPermission()

        register()
    }

    override fun onPause() {
        super.onPause()
        mBluetoothReceiver.unRegister(_this)

        unregister()
    }

    private fun register() {
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(mReceiver, filter)
    }

    private fun unregister() {
        unregisterReceiver(mReceiver)
    }

    private fun requestPermission() {
        mPermissions.request(Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe({
                    enableBluetooth()
                }, {
                    toast("should request bluetooth permission")
                    finish()
                })
    }

    fun enableBluetooth() {
        //                        mBtAdapter.enable()
        mBtAdapter.takeIf { it.isDisabled }?.apply {
            val enableIntent= Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT)
        }
    }

    open fun onBluetoothEnable() {
    }

    override fun onStateChange(state: Int) {
    }

    override fun onFound(device: BluetoothDevice) {
    }

    override fun onDisappeared(device: BluetoothDevice) {
    }

    override fun onPairing(device: BluetoothDevice) {
    }

    override fun onBondChange(device: BluetoothDevice) {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ENABLE_BT -> {
                if (resultCode == Activity.RESULT_OK) {

                    onBluetoothEnable()
                } else {
                    onBluetoothDisable()
                }
            }
        }
    }

    private fun onBluetoothDisable() {
        finish()
    }
}