package com.lanayru.app.ui

import android.os.Bundle
import com.lanayru.app.ui.base.BaseActivity
import org.jetbrains.anko.verticalLayout
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView



/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/11/14
 *
 **/
class GifActivity: BaseActivity() {

    private lateinit var mIv1: GifImageView
    private lateinit var mIv2: GifImageView

    override fun render(savedInstanceState: Bundle?) {
        val root = verticalLayout {

        }

        mIv1 = GifImageView(_this)
        mIv2 = GifImageView(_this)

        root.addView(mIv1)
        root.addView(mIv2)

        setContentView(root)

        renderGif()
    }

    private fun renderGif() {
        val g1 = GifDrawable(assets, "musiclens_discovery_1.gif")
        val g2 = GifDrawable(assets, "musiclens_discovery_2.gif")

        mIv1.setImageDrawable(g1)
        mIv2.setImageDrawable(g2)
    }
}