package com.lanayru.app.ui.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.lanayru.library.ui.base.RvAdapter
import com.lanayru.view.BluetoothItemView
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 *
 * Scan bluetooth devices
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/17
 *
 **/
class ScanActivity : BluetoothActivity() {

    companion object {
        private const val SCANNING_TIME = 30000L
    }

    private lateinit var mRecyclerView: RecyclerView

    private lateinit var mAdapter: RvAdapter<BluetoothDevice>

    private val mHandler = Handler()

    override fun render(savedInstanceState: Bundle?) {

        renderList()
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
                return BluetoothItemView(parent?.context).apply {
                    setOnClickListener {v->
//                        bond((it as BluetoothItemView).data!!)
                        val itemView = v as BluetoothItemView
                        itemView.data?.address?.let {
                            startActivity<BtClientActivity>(Pair("mac", it))
                        }
                    }
                }
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

    /**
     * 搜索经常会失败，可以优化下搜索，如失败重新搜索
     */
    private fun startScan() {
        if (mBtAdapter.isEnabled) {
            if (!mBtAdapter.startDiscovery()) {
                toast("start error!")
            } else {
                toast("startDiscovery")
            }
            mHandler.postDelayed({
                stopScan()
            }, SCANNING_TIME)
        }
    }

    private fun stopScan() {
        if (mBtAdapter.isEnabled && mBtAdapter.isDiscovering) {
            mBtAdapter.cancelDiscovery()
        }
    }

    private fun bond(device: BluetoothDevice) {
        val success = device.createBond()
        if (success) {
            toast("正在配对")
        } else {
            toast("配对失败")
        }
    }

    override fun onFound(device: BluetoothDevice) {
        super.onFound(device)
        if (device?.name != null) {
            mAdapter.addOrUpdate(device)
        }
    }

    override fun onDisappeared(device: BluetoothDevice) {
        super.onDisappeared(device)
        mAdapter.remove(device)
    }

    override fun onStateChange(state: Int) {
        super.onStateChange(state)
        when (state) {
            BluetoothAdapter.STATE_ON -> {
                startScan()
            }

            BluetoothAdapter.STATE_OFF -> {
                stopScan()
            }
        }
    }


}