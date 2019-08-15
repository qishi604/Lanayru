package com.lanayru.app.ui.album

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.lanayru.library.image.GlideApp
import com.lanayru.library.ui.base.BaseActivity
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * @author 郑齐
 * @since 2019-07-05
 * @version V1.0
 *
 */
class DetailActivity: BaseActivity() {

    lateinit var mIvImage: ImageView

    lateinit var mDecorView: View

    override fun renderToolbar() {
//        super.renderToolbar()
    }

    override fun render(savedInstanceState: Bundle?) {
        mDecorView = window.decorView
        mIvImage = ImageView(_this)

        setContentView(mIvImage)

        var url = intent.getStringExtra("url")
        if (null != url) {
            GlideApp.with(mIvImage).load(url).into(mIvImage)
        }

        mIvImage.onClick {
            toggleSystemUI()
        }

        setFullScreen()
        toggleSystemUI()
    }

    private fun isFullScreen(): Boolean {
        return mDecorView.getSystemUiVisibility() and View.SYSTEM_UI_FLAG_FULLSCREEN == View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun toggleSystemUI() {
        if (isFullScreen()) {
            showSystemUI()
        } else {
            hideSystemUI()
        }

//        if (ScreenUtils.isFullScreen(_this)) {
//            ScreenUtils.setNonFullScreen(_this)
//        } else {
//            ScreenUtils.setFullScreen(_this)
//        }
    }

    private fun setFullScreen() {
        // 适配刘海屏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            val attributes = window.attributes
//            attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
//            window.attributes = attributes
//        } else {
//            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        }
    }

    private fun hideSystemUI() {
        // 适配刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val attributes = window.attributes
            attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = attributes
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                        or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    private fun showSystemUI() {
        // 适配刘海屏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            val attributes = window.attributes
//            attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT
//            window.attributes = attributes
//
//        } else {
//            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        }

        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        )
    }
}