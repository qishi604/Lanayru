package com.lanayru.util

import android.content.Context
import android.os.Environment
import com.blankj.utilcode.util.Utils
import java.io.File

/**
 *
 * App file util
 *
 * need request android.permission.WRITE_EXTERNAL_STORAGE
 *
 * @author seven
 * @since 2018/7/25
 * @version V1.0
 */
object Files {

    private val ROOT = Utils.getApp().packageName

    fun getPublicRootDir() = File(Environment.getExternalStorageDirectory(), ROOT).apply { mkdirs() }

    fun getPublicDir(subPath: String) = File(getPublicRootDir(), subPath).apply { mkdirs() }

    fun getDexPath(context: Context) = context.getDir("_dex", Context.MODE_PRIVATE).absolutePath

}