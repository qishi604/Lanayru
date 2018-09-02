package com.lanayru.app.ui.classloader

import android.content.res.Resources
import android.os.Bundle
import android.widget.ImageView
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.util.ApkUtil
import com.lanayru.util.Files
import dalvik.system.DexClassLoader
import org.jetbrains.anko.button
import org.jetbrains.anko.imageView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.verticalLayout

/**
 *
 * @author seven
 * @since 2018/7/23
 * @version V1.0
 */
class DexClassLoaderActivity: BaseActivity() {

    val apk_path = "/sdcard/Download/s1.apk"
    val skin_path = apk_path
//    val skin_path = "file:///android_asset/skin/s1.apk"

    var mClassLoader: DexClassLoader? = null

    lateinit var mIvAvatar: ImageView

    lateinit var mRes: Resources

    override fun render(savedInstanceState: Bundle?) {

        installApk()

        verticalLayout {
            button("Show log") {
                onClick { showlog("dex class loader log") }
            }

            mIvAvatar = imageView{}.lparams(120.dp, 120.dp)

        }

    }

    private fun installApk() {
        mClassLoader = DexClassLoader(apk_path, Files.getDexPath(_this), applicationInfo.nativeLibraryDir, classLoader)
        mRes = ApkUtil.getResource(_this, skin_path)
    }

    private fun showlog(s: String) {
        mClassLoader?.let {

            val cls = it.loadClass("com.lanayru.util.Logs")
            val oj = cls.newInstance()
            val m = cls.getDeclaredMethod("i", String::class.java)
            m.invoke(oj, "===============000000000000000------------000000================")
        }

//        val pkg= "com.lanayru"
//        val id = mRes.getIdentifier("avatar_default", "drawable", pkg)
//        val colorid = mRes.getIdentifier("colorAccent", "color", pkg)

        mIvAvatar.setImageDrawable(mRes.getDrawable(R.drawable.avatar_default))
        mIvAvatar.setBackgroundColor(mRes.getColor(R.color.colorAccent))
    }
}