package com.lanayru.app.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.library.ui.base.RvAdapter
import com.lanayru.model.Event
import com.lanayru.util.Logs
import com.lanayru.view.ActivityItemView
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity(), AnkoLogger {

    companion object {
        var sMainActivity: MainActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        sMainActivity = this
        // 恢复默认主题
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
    }

    private lateinit var mAdapter: RvAdapter<ActivityInfo>

    override fun render(savedInstanceState: Bundle?) {
        logClassLoader()

        mAdapter = object : RvAdapter<ActivityInfo>() {
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

        mAdapter.data = data

        val rv = RecyclerView(_this).apply {
            layoutManager = LinearLayoutManager(_this, LinearLayoutManager.VERTICAL, false)
            this.adapter = mAdapter
        }

        val root = applyRefresh()
        root.addView(rv)

        setContentView(root)
    }

    private fun applyRefresh(): ViewGroup {
        return SmartRefreshLayout(this).apply {
            setRefreshHeader(ClassicsHeader(_this))
            setRefreshFooter(ClassicsFooter(_this))
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

    fun onEvent(ev: Event) {
        toast(ev.data.toString())
        Logs.i("event ${ev.data.toString()}")
        mAdapter.notifyDataSetChanged()
    }
}
