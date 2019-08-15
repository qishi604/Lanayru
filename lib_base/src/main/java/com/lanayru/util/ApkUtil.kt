package com.lanayru.util

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources

/**
 *
 * @author seven
 * @since 2018/7/24
 * @version V1.0
 */
object ApkUtil {

    fun getResource(context: Context, apkPath: String): Resources {
        val assetManager = AssetManager::class.java.newInstance()
        val addAssetPath = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
        addAssetPath.invoke(assetManager, apkPath)
        val resources = context.resources
        return Resources(assetManager, resources.displayMetrics, resources.configuration)
    }

    fun getPackageName(context: Context, apkPath: String) = context.packageManager.getPackageArchiveInfo(apkPath, 0)
            ?.run { packageName }



}