package com.lanayru.app.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity

/**
 * @author 郑齐
 * @since 2019-07-10
 * @version V1.0
 *
 */
class TestThemeActivity: BaseActivity() {

    override fun renderToolbar() {
//        super.renderToolbar()
    }

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_image_preview)

        translucent()
    }

    private fun translucent() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        window.statusBarColor = Color.TRANSPARENT

        showSystemUI()
    }

    // This snippet hides the system bars.
    private fun hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                )
    }
}