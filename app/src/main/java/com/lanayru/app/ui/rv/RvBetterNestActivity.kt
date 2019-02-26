package com.lanayru.app.ui.rv

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.app.ui.base.RvAdapter
import com.lanayru.extention.dp
import com.lanayru.view.ImageItemView
import com.lanayru.view.rv.BetterRecyclerView
import com.lanayru.view.rv.RootRecyclerView

/**
 * 测试横竖嵌套的 RecyclerView
 */
class RvBetterNestActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        val adapter= object : RvAdapter<String>() {
            override fun onCreateView(parent: ViewGroup?, viewType: Int): View {
                return renderNestRv()
            }
        }

        val data = MutableList(10) {
            "$it"
        }

        adapter.data = data
        val rv = RootRecyclerView(_this).apply {
            layoutManager = LinearLayoutManager(_this, LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }

        setContentView(rv)
    }

    private fun renderNestRv(): RecyclerView {
        val adapter= object : RvAdapter<String>() {
            override fun onCreateView(parent: ViewGroup?, viewType: Int): View {
                val itemView = ImageItemView(_this).apply {
                    setBackgroundColor(Color.BLUE)
                }

                itemView.layoutParams = RecyclerView.LayoutParams(100.dp, 100.dp).apply {
                    leftMargin = 10.dp
                    bottomMargin = 10.dp
                }

                return itemView
            }
        }

        val data = MutableList<String>(20) {
            "$it"
        }

        adapter.data = data
        return BetterRecyclerView(_this).apply {
            layoutManager = LinearLayoutManager(_this, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = adapter
        }

    }

}