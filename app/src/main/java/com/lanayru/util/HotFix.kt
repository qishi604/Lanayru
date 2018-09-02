package com.lanayru.util

import android.app.Application
import dalvik.system.BaseDexClassLoader
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.lang.reflect.Array

/**
 *
 * @author seven
 * @since 2018/7/30
 * @version V1.0
 */
object HotFix {

    val patch_path = "/sdcard/com.lanayru/patch/fix_dex.jar"

    /**
     * 未考虑android版本适配问题
     */
    fun patch(app: Application) {
        val patchFile = File(patch_path)
        if (!patchFile.exists()) {
            return
        }

        val pathClassLoader = app.classLoader as PathClassLoader

        val pathList = getPathList(pathClassLoader)

        // 插件
        val dexClassLoader = DexClassLoader(patch_path, Files.getDexPath(app), null, pathClassLoader)

        val patchElements = combineArray(getDexElements(pathClassLoader), getDexElements(dexClassLoader))

        setField(pathList, "dexElements", patchElements)
    }

    private fun getPathList(loader: BaseDexClassLoader) = getField(loader, "pathList")!!

    private fun getDexElements(loader: BaseDexClassLoader) = getField(getPathList(loader)!!, "dexElements")!!

    /**
     * 合并数组，将obj2放到前面
     */
    private fun combineArray(obj1: Any, obj2: Any): Any {
        val type = obj2.javaClass.componentType

        val len1 = Array.getLength(obj1)
        val len2 = Array.getLength(obj2)

        val len = len1 + len2
        val newArray = Array.newInstance(type, len)

        for (i in 0 until len) {

            val o = if (i < len1) {
                Array.get(obj2, i)
            } else {
                Array.get(obj1, i - len2)
            }

            Array.set(newArray, i, o)
        }

        return newArray
    }
}