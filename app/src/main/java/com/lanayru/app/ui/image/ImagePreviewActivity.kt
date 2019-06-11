package com.lanayru.app.ui.image

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.lanayru.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_image_preview.*




/**
 * @author 郑齐
 * @since 2019/5/30
 * @version V1.0
 *
 */
class ImagePreviewActivity : BaseActivity() {

    lateinit var mDecorView: View

    lateinit var mIvImage: ImageView

    override fun render(savedInstanceState: Bundle?) {
        setContentView(com.lanayru.app.R.layout.activity_image_preview)
        mDecorView = window.decorView
        toolbar.title = "Preview"

        mIvImage = iv_image

        mIvImage.setOnClickListener {
            toggleSystemUI()
        }

        mDecorView.setOnSystemUiVisibilityChangeListener {
            if (isFullScreen()) {
                hideBar()
            } else {
                showBar()
            }
        }

//        hideSystemUI()
        showSystemUI()
    }

    override fun renderToolbar() {
//        super.renderToolbar()
    }

    private fun isFullScreen() = mDecorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN == View.SYSTEM_UI_FLAG_FULLSCREEN

    private fun toggleSystemUI() {
        if (isFullScreen()) {
            showSystemUI()
        } else {
            hideSystemUI()
        }
    }

    private fun showBar() {
        topbar.visibility = View.VISIBLE
        bottombar.visibility = View.VISIBLE
    }

    private fun hideBar() {
        topbar.visibility = View.GONE
        bottombar.visibility = View.GONE

    }

    // This snippet hides the system bars.
    private fun hideSystemUI() {
        // Set the IMMERSIVE flag.
        // Set the content to appear under the system bars so that the content
        // doesn't resize when the system bars hide and show.
        mDecorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar

                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar

                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    // This snippet shows the system bars. It does this by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        mDecorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                )
    }
}