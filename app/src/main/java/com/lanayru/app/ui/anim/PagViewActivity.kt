package com.lanayru.app.ui.anim

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.extention.dp
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent
import org.libpag.PAGFile
import org.libpag.PAGView

class PagViewActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        val _root = frameLayout(){
            setBackgroundResource(R.drawable.bg_common)
        }


        val lp1 = LinearLayout.LayoutParams(80.dp, 80.dp).apply {
        }
        val lp2 = LinearLayout.LayoutParams(100.dp, 100.dp)
//        _root.addView(renderPag("pag/global_play_loading.pag"), lp1)
//        _root.addView(renderPag("pag/heart.pag"), lp2)
//        _root.addView(renderPag("pag/user_guide_card.pag"))

        val _content = LinearLayout(_this).apply{
            orientation = LinearLayout.VERTICAL
        }
        _content.addView(renderPag("bottle_spot_1.pag"), lp1)
        _content.addView(renderPag("bottle_spot_2.pag"), lp1)
        _content.addView(renderPag("bottle_spot_3.pag"), lp1)
        _content.addView(renderPag("bottle_spot_4.pag"), lp1)
        _content.addView(renderPag("loading.pag"), lp1)
        _content.addView(renderPag("tide.pag"), lp1)
        _content.addView(renderPag("wave1.pag"), lp1)

        _root.addView(renderPag("index_bg.pag"))
        _root.addView(_content)

        // mask
        val mask = View(_this).apply {
            setBackgroundResource(R.drawable.mask_bg)
        }
        _root.addView(mask, ViewGroup.LayoutParams(matchParent, matchParent))

        setContentView(_root)
    }

    private fun renderPag(file: String): View {
        val pag = PAGView(_this)
        pag.file = PAGFile.Load(assets, file)
        pag.progress = 0.0
        pag.setLoop(true)
        pag.play()
        return pag
    }
}