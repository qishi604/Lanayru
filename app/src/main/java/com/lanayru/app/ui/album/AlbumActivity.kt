package com.lanayru.app.ui.album

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils
import com.lanayru.GlideApp
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.app.ui.base.RvAdapter
import com.lanayru.data.AlbumProvider
import com.lanayru.extention.dp
import com.lanayru.model.Media
import com.lanayru.util.Logs
import com.lanayru.util.MemoryLog
import com.lanayru.view.IDateView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class AlbumActivity : BaseActivity() {

    companion object {
        val SPACE = 4.dp
    }

    lateinit var mInterval: Disposable

    private lateinit var mAlloc: ArrayList<Int>

    override fun render(savedInstanceState: Bundle?) {

        Logs.duration {
            allocMemory()
        }

        val adapter = object : RvAdapter<Media>() {
            override fun onCreateView(parent: ViewGroup?, viewType: Int): View {
                return ItemView(parent!!.context)
            }
        }

        val rv = RecyclerView(_this).apply {
            setPadding(0,0, SPACE,0)
            clipToPadding = false
            layoutManager = GridLayoutManager(_this, 4)

            this.adapter = adapter
        }

        setContentView(rv)

        val subscribe = RxPermissions(_this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe {
            if (it) {
                AlbumProvider.getData(loaderManager = supportLoaderManager) {
                    adapter.data = ArrayList(it)
                }
            }
        }

        mInterval = Observable.interval(1, TimeUnit.SECONDS).subscribe {
            MemoryLog.log()
        }
    }

    class ItemView(context: Context?) : ImageView(context), IDateView<Media> {

        init {
            scaleType = ScaleType.CENTER_CROP

            val size = (ScreenUtils.getScreenWidth() - 5 * SPACE) / 4
            layoutParams = ViewGroup.MarginLayoutParams(size, size).apply {
                leftMargin = SPACE
                bottomMargin = SPACE
            }
        }

        override var data: Media? = null
            set(value) {
                field = value
                GlideApp.with(this).load(value!!.path).into(this)
            }
    }

    /**
     * 申请了这么多内存，再加载图片都没有 OOM，车信的有可能是图片加载库，或者后台闪退
     */
    private fun allocMemory() {
        val size = 1000 * 1000 * 10
        mAlloc = ArrayList(size)
        for (i in 0..size) {
            mAlloc.add(i)
        }
    }

    override fun onDestroy() {
        mInterval.dispose()
        super.onDestroy()
    }
}