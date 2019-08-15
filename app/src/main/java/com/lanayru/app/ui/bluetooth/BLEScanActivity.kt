package com.lanayru.app.ui.bluetooth

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.lanayru.library.ui.base.RvAdapter
import com.lanayru.view.BluetoothItemView

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/18
 *
 **/
class BLEScanActivity: BluetoothActivity() {

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: RvAdapter<BluetoothDevice>

    private var mScanning = false

    private lateinit var mCallback: ScanCallback

    override fun render(savedInstanceState: Bundle?) {
        renderList()

        mCallback = object : ScanCallback() {

            override fun onScanResult(callbackType: Int, result: ScanResult?) {
                super.onScanResult(callbackType, result)
                if (null == result) {
                    return
                }

                onFound(result.device)
            }
        }
    }

    private fun renderList() {
        mRecyclerView = RecyclerView(_this).apply {
            layoutManager = object : LinearLayoutManager(_this) {
                override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                    return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                }
            }
        }

        setContentView(mRecyclerView)

        mAdapter = object : RvAdapter<BluetoothDevice>() {
            override fun onCreateView(parent: ViewGroup?, viewType: Int): View {
                return BluetoothItemView(parent?.context)
            }
        }

        mRecyclerView.adapter = mAdapter

        mBtAdapter.bondedDevices?.let {
            if (it.isNotEmpty()) {
                it.forEach {
                    mAdapter.addOrUpdate(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startScan()
    }

    override fun onPause() {
        super.onPause()
        stopScan()
    }

    fun startScan() {
        if (!mBtAdapter.isEnabled) {
            return
        }
        mScanning = true

        val scanner = mBtAdapter.bluetoothLeScanner
        scanner.startScan(mCallback)
    }

    fun stopScan() {
        if (!mBtAdapter.isEnabled) {
            return
        }
        mScanning = false

        val scanner = mBtAdapter.bluetoothLeScanner
        scanner.stopScan(mCallback)
    }

    override fun onFound(device: BluetoothDevice) {
        super.onFound(device)
        mAdapter.addOrUpdate(device)
    }
}