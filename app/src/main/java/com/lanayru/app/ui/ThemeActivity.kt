package com.lanayru.app.ui

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.library.ui.base.RvAdapter
import com.lanayru.extention.dp
import com.lanayru.theme.Skin
import com.lanayru.theme.ThemeHelper
import com.lanayru.view.SpaceItemDecoration
import com.lanayru.view.ThemeItemView

/**
 *
 * @author seven
 * @since 2018/7/25
 * @version V1.0
 */
class ThemeActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        val adapter = object : RvAdapter<Skin>() {

            override fun onCreateView(parent: ViewGroup?, viewType: Int) = ThemeItemView(_this)
        }.apply {
            onItemClick = {_, d ->
                d?.let {
                    ThemeHelper.switch(it)
                }
            }

//            data = mutableListOf(
//                    Skin(0xFF3F51B5.toInt(), 0xFF303F9F.toInt(), 0xFFFF4081.toInt()),
//                    Skin(0xFFFCB8AB.toInt(), 0xFFFFDBCF.toInt(), 0xFFE05400.toInt()),
//                    Skin(0xFF0097a7.toInt(), 0xFF006978.toInt(), 0xFF5f5fc4.toInt()),
//                    Skin(0xFF76ff03.toInt(), 0xFF32cb00.toInt(), 0xFFf9683a.toInt())
//            )

            data = ThemeHelper.getData()
        }

        RecyclerView(_this).apply {
            setPadding(0, 12.dp, 0, 0)
            clipToPadding = false
            layoutManager = GridLayoutManager(_this, 2)
            this.adapter = adapter
            addItemDecoration(SpaceItemDecoration(12.dp))

        }.run {
            setContentView(this)
        }
    }
}