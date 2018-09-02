package com.lanayru.app.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.app.ui.base.RvAdapter
import com.lanayru.view.ActivityItemView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 恢复默认主题
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    override fun render(savedInstanceState: Bundle?) {
        Thread.sleep(1000)

        logClassLoader()

        val adapter = object : RvAdapter<ActivityInfo>() {
            override fun onCreateView(parent: ViewGroup?, viewType: Int) = ActivityItemView(_this)
        }.apply {
            onItemClick = { _, d ->
                d?.let {
                    Intent().apply {
                        setClassName(_this, it.name)
                    }.run {
                        startActivity(this)
                    }
                }
            }
        }

        val data = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).activities
                .filter { it.name != MainActivity::class.java.name }
                .let {
                    MutableList(it.size) { i -> it[i] }
                }
                .apply {
                    reverse()
                }

        adapter.data = data

        RecyclerView(_this).apply {
            layoutManager = LinearLayoutManager(_this, LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter
        }.run {
            setContentView(this)
        }
    }

    override fun renderToolbar() {
        super.renderToolbar()
        _toolbar?.run {
            inflateMenu(R.menu.menu_main)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_settings -> {

                    }

                    R.id.action_theme -> {
                        startActivity<ThemeActivity>()
                    }
                }
                true
            }
        }
    }

    fun logClassLoader() {
        var classLoader = getClassLoader()
        while (null != classLoader) {
            info { "class loader ${classLoader.toString()}" }
            classLoader = classLoader.parent
        }

    }
}
