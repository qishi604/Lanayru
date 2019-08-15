package com.lanayru.app.ui.album

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.ObjectUtils
import com.blankj.utilcode.util.ScreenUtils
import com.lanayru.library.image.ImageLoader
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.library.ui.base.RvAdapter
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

open class AlbumActivity : BaseActivity() {

    companion object {
        val SPACE = 4.dp
    }

    lateinit var mInterval: Disposable

    private lateinit var mAlloc: ArrayList<Int>

    override fun render(savedInstanceState: Bundle?) {

        Logs.duration {
            allocMemory()
        }

        logFrame()

        val adapter = object : RvAdapter<Media>() {
            override fun onCreateView(parent: ViewGroup?, viewType: Int): View {
                return ItemView(parent!!.context)
            }
        }

        val rv = RecyclerView(_this).apply {
            setPadding(0,SPACE, SPACE,0)
            setHasFixedSize(true)
            clipToPadding = false
            layoutManager = GridLayoutManager(_this, 4)

            this.adapter = adapter
        }

        setContentView(rv)

        val subscribe = RxPermissions(_this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe {
            if (it) {
                AlbumProvider.getData(loaderManager = supportLoaderManager) {list->
                    if (ObjectUtils.isNotEmpty(list)) {
                        adapter.data = ArrayList(list)
                    }
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

            setOnClickListener {
                val ctx = context!!
                val url = data!!.path
                val intent = Intent(ctx, DetailActivity::class.java).apply {
                    putExtra("url", url)
                }
                context!!.startActivity(intent)
            }
        }

        override var data: Media? = null
            set(value) {
                field = value
                ImageLoader.load(this, value!!.path, R.drawable.img_loading_placeholder)
            }
    }

    /**
     * 申请了这么多内存，再加载图片都没有 OOM，车信的有可能是图片加载库，或者后台闪退
     */
    private fun allocMemory() {
        val size = 1000 * 1000 * 4
        mAlloc = ArrayList(size)
        for (i in 0..size) {
            mAlloc.add(i)
        }
    }

    private fun logFrame() {
        Choreographer.getInstance().postFrameCallback {
            println("doFrame $it")
        }
    }

    override fun onDestroy() {
        mInterval.dispose()
        super.onDestroy()
    }
}