package com.lanayru.app.ui.bluetooth

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.util.execute
import kotlinx.android.synthetic.main.activity_bt_client_activity.*
import org.jetbrains.anko.toast

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/19
 *
 **/
class BtClientActivity: BluetoothActivity() {

    private lateinit var mSocket: BluetoothSocket

    override fun render(savedInstanceState: Bundle?) {
        title = "Client"
        setContentView(R.layout.activity_bt_client_activity)

        btn_send.setOnClickListener {
            val s = et_content.text.toString()
            if (s.isEmpty()) {
                return@setOnClickListener
            }
            et_content.setText("")
            sendMsg(s)
        }

        btn_connect.setOnClickListener {
            connect()
        }

        initSocket()
    }

    private fun initSocket() {
        val address = intent.getStringExtra("mac")

        try {
            val device = mBtAdapter.getRemoteDevice(address)
            tv_content.text = "连接${device.name}(${device.address}) ${device.type}"
            mSocket = device.createRfcommSocketToServiceRecord(UUIDs.SPP)

        } catch (e: Exception) {
            e.printStackTrace()
            toast(e.message?: "初始化失败！")
        }

        connect()
    }

    private fun connect() {
        if (null == mSocket) {
            toast("socket 为空")
            return
        }

        if (mSocket.isConnected) {
            toast("已连接")
            return
        }

        btn_connect.text = "正在连接..."
        execute {
            var text = try {
                mSocket.connect()
                processRead()
                "连接成功"
            } catch (e: Exception) {
                "连接失败，点击重试"
            }

            runOnUiThread {
                btn_connect.text = text
            }
        }
    }

    private fun processRead() {
        execute {
            val buf = ByteArray(128)
            mSocket.inputStream.let {
                while (true) {
                    try {
                        val count = it.read(buf)
                        if (count > 0) {
                            val msg = String(buf, 0, count)
                            postMessage(msg)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        runOnUiThread {
                            btn_connect.text = "连接断开"
                        }
                        closeSocket()
                        break
                    }
                }
            }
        }
    }

    private fun closeSocket() {
        try {
            mSocket.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendMsg(msg: String) {
        try {
            mSocket?.run {
                outputStream.write(msg.toByteArray())
            }
        } catch (e: Exception) {
            toast(e.message?: "连接关闭")
        }
    }

    private fun postMessage(msg: String) {
        runOnUiThread {
            tv_content.append("$msg \n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        closeSocket()
    }
}