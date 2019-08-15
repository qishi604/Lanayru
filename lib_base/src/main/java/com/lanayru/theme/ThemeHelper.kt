package com.lanayru.theme

import android.content.res.Resources
import android.support.v4.content.res.ResourcesCompat
import com.blankj.utilcode.util.Utils
import com.lanayru.library.R
import com.lanayru.util.ApkUtil
import com.lanayru.util.Files
import java.util.*

/**
 *
 * @author seven
 * @since 2018/7/24
 * @version V1.0
 */
object ThemeHelper : Observable() {

    private val _dir = "skin"

    private var _data: MutableList<Skin>? = null

    private fun fromResource(res: Resources, packageName: String) = Skin(
            getColor(res, "colorPrimary", packageName),
            getColor(res, "colorPrimaryDark", packageName),
            getColor(res, "colorAccent", packageName)
    )

    private fun fromResource(res: Resources) = Skin(
            getColor(res, R.color.colorPrimary),
            getColor(res, R.color.colorPrimaryDark),
            getColor(res, R.color.colorAccent)
    )

    private fun getColor(res: Resources, id: Int) = ResourcesCompat.getColor(res, id, null)

    private fun getColor(res: Resources, name: String, packageName: String): Int {
        val id = res.getIdentifier(name, "color", packageName)
        return ResourcesCompat.getColor(res, id, null)
    }

    var skin: Skin = fromResource(Utils.getApp().resources)

    fun getData(): MutableList<Skin>? {
        if (null == _data) {
            load()
        }
        return _data
    }

    fun load(): MutableList<Skin>? {
        val dir = Files.getPublicDir(_dir)
        val list = dir.listFiles { _, name -> name.endsWith(".apk") }
        if (list.isNotEmpty()) {
            _data = MutableList(list.size) {
                val apkPath = list[it].absolutePath
                val res = ApkUtil.getResource(Utils.getApp(), apkPath)
                fromResource(res)
            }
        }

        return _data
    }

    fun switch(s: Skin) {
        skin = s
        setChanged()
        notifyObservers()
    }
}