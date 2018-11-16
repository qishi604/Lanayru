package com.lanayru.app.ui.bluetooth

import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.widget.TextView
import com.lanayru.util.execute
import org.jetbrains.anko.textView
import org.jetbrains.anko.toast
import org.jetbrains.anko.verticalLayout

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/19
 *
 **/
class BtServerActivity: BluetoothActivity() {

    private lateinit var mTvContent: TextView

    private lateinit var mServerSocket: BluetoothServerSocket

    private var mBluetoothSocket: BluetoothSocket? = null

    private var mReceiveCount = 0

    override fun render(savedInstanceState: Bundle?) {
        title = "Server"
        val root = verticalLayout {
            mTvContent = textView()
        }

        setContentView(root)

        initSocket()
    }

    private fun initSocket() {
        mServerSocket = mBtAdapter.listenUsingRfcommWithServiceRecord("bt", UUIDs.SPP)

        execute {
            var socket: BluetoothSocket? = null

            while (true) {

                try {
                    socket = mServerSocket.accept()
                    mBluetoothSocket = socket
                } catch (e: Exception) {
                    e.printStackTrace()
                    break
                }

                if (null != socket) {
                    postMessage("${socket.remoteDevice.name}(${socket.remoteDevice.address}) 接入")

                    read(socket)
                    mServerSocket.close()
                    break
                }
            }
        }
    }

    private fun read(s: BluetoothSocket) {
        execute {
            val inputStream = s.inputStream
            val buf = ByteArray(128)

            while (true) {
                try {
                    val count = inputStream.read(buf)
                    write(s, "Receive ${++mReceiveCount}")
                    val msg = String(buf, 0, count)
                    postMessage(msg)
                } catch (e: Exception) {
                    e.printStackTrace()
                    s.close()
                    postMessage("连接关闭")
                    break
                }
            }
        }
    }

    private fun write(s: BluetoothSocket, msg: String) {

        try {
            s.outputStream.write(msg.toByteArray())
        } catch (e: Exception) {
            toast(e.message?: "连接断开")
        }
    }

    private fun postMessage(msg: String) {
        runOnUiThread{
            mTvContent.append("$msg \n")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mBluetoothSocket?.close()
    }
}