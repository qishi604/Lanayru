package com.lanayru.app.ui.album

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.blankj.utilcode.util.ScreenUtils
import com.lanayru.library.image.ImageLoader
import com.lanayru.app.R
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.library.ui.base.RvAdapter
import com.lanayru.data.AlbumProvider
import com.lanayru.extention.dp
import com.lanayru.model.Media
import com.lanayru.util.MemoryLog
import com.lanayru.view.IDateView
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.*
import java.util.concurrent.TimeUnit

class VideosActivity : BaseActivity() {

    companion object {
        val SPACE = 4.dp

        private inline fun o(v: Long): String {
            return if (v < 10) {
                "0$v"

            } else {
                "$v"
            }
        }

        fun formatDuration(v: Long): String {
            if (v < 0) {
                return ""
            }

            var second = v / 1000
            if (second < 60) {
                return "00:${o(second)}"
            }

            var minute = second / 60
            second %= 60

            if (minute < 60) {
                return "${o(minute)}:${o(second)}"
            }

            var hour = minute / 60
            minute %= 60

            return "${o(hour)}:${o(minute)}:${o(second)}"
        }
    }

    lateinit var mInterval: Disposable

    override fun render(savedInstanceState: Bundle?) {

        val adapter = object : RvAdapter<Media>() {
            override fun onCreateView(parent: ViewGroup?, viewType: Int): View {
                return ItemView(parent!!.context)
            }
        }

        val rv = RecyclerView(_this).apply {
            setPadding(0, SPACE, SPACE,0)
            clipToPadding = false
            layoutManager = GridLayoutManager(_this, 4)

            this.adapter = adapter
        }

        setContentView(rv)

        val subscribe = RxPermissions(_this).request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe {
            if (it) {
                AlbumProvider.getVideo(loaderManager = supportLoaderManager) {
                    adapter.data = ArrayList(it)
                }
            }
        }

        mInterval = Observable.interval(1, TimeUnit.SECONDS).subscribe {
            MemoryLog.log()
        }
    }

    class ItemView(context: Context?) : FrameLayout(context), IDateView<Media> {

        val size = (ScreenUtils.getScreenWidth() - 5 * SPACE) / 4

        private val mIv: ImageView = ImageView(context).apply {
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        private val mTvDuration: TextView = TextView(context).apply {
            textColor = Color.WHITE
            setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_videocam, 0, 0, 0)
            gravity = Gravity.CENTER_VERTICAL
            padding = 4.dp
            backgroundColor = 0x80000000.toInt()
            compoundDrawablePadding = 4.dp
        }

        init {
            addView(mIv, ViewGroup.LayoutParams(matchParent, matchParent))
            addView(mTvDuration, LayoutParams(matchParent, wrapContent).apply {
                gravity = Gravity.BOTTOM
            })

            layoutParams = MarginLayoutParams(size, size).apply {
                leftMargin = SPACE
                bottomMargin = SPACE
            }
        }

        override var data: Media? = null
            set(value) {
                field = value
                ImageLoader.load(mIv, value!!.path)
                mTvDuration.text = formatDuration(value!!.duration)
            }
    }

    override fun onDestroy() {
        mInterval.dispose()
        super.onDestroy()
    }
}