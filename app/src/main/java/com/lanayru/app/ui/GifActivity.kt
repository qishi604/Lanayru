package com.lanayru.app.ui

import android.os.Bundle
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.util.AssetGifLoader
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

        root.addView(mIv1, ViewGroup.MarginLayoutParams(120.dp, 120.dp))
        root.addView(mIv2)

        setContentView(root)

//        renderGif()
        renderGif1()
    }

    private fun renderGif() {
        val g1 = GifDrawable(assets, "musiclens_discovery_1.gif")
        val g2 = GifDrawable(assets, "musiclens_discovery_2.gif")

        mIv1.setImageDrawable(g1)
        mIv2.setImageDrawable(g2)
    }

    private fun renderGif1() {
        AssetGifLoader.load(mIv1, "g1_10.gif")
        Glide.with(_this)
                .load(AssetGifLoader.getPath("g1_10.gif"))
                .into(mIv2)
    }

    private fun testGlide() {
//        GlideApp.with(this).load("").into(mIv1)
//        Glide.with(this).load("").into(mIv1)
    }
}