package com.lanayru.app.ui.lottie

import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lottie.*

/**
 * @author 郑齐
 * @since 2019-08-02
 * @version V1.0
 *
 */
class LottieActivity: BaseActivity() {
    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_lottie)
        loadAnim()
    }

    private fun loadAnim() {
        animationView.imageAssetsFolder = "lottie/emoji_images"
        animationView.setAnimation("lottie/emoji_data.json")
        animationView.loop(true)
        animationView.playAnimation()
    }
}