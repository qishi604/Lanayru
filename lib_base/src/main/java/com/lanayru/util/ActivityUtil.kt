package com.lanayru.util

import android.content.Intent
import android.os.IBinder
import android.os.Parcel

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/11/12
 *
 **/
object ActivityUtil {

    /**
     * 没效果～，有空看看是啥问题
     */
    fun startActivityAMS(intent: Intent): Boolean {
        val serviceData = Parcel.obtain().apply {
            writeInterfaceToken("android.app.IActivityManager")
            writeStrongBinder(null)
            writeString(intent.`package`)
        }
        intent.writeToParcel(serviceData, 0)

        try {// reflect
            val activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative")
            val activityManagerNative = activityManagerNativeClass.getMethod("getDefault").invoke(activityManagerNativeClass)
            val mRemoteField = activityManagerNative.javaClass.getDeclaredField("mRemote")
            mRemoteField.isAccessible = true
            val mRemote = mRemoteField.get(activityManagerNative) as IBinder

            // START_ACTIVITY_TRANSACTION = 3
            mRemote.transact(3, serviceData, null, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }
}