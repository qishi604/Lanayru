package com.lanayru.app.ui

import android.Manifest
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import com.chenenyu.router.annotation.Route
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.player.FlacPlayer
import com.lanayru.util.getField
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_music_player.*
import org.jetbrains.anko.toast
import java.io.File

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/3
 *
 **/
@Route("music/player")
class MusicPlayerActivity: BaseActivity() {

    private lateinit var mPlayer: FlacPlayer

    private lateinit var mPermissions: RxPermissions

    private var mIsPlay = false

    override fun render(savedInstanceState: Bundle?) {
        mPermissions = RxPermissions(this)
        setContentView(R.layout.activity_music_player)

        checkFile()

        iv_play.setOnClickListener {
            if (mIsPlay) {
                pause()
            } else {
                play()
            }
        }
    }

    private fun checkFile() {
        mPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(
                {
                    if (it) {
                        prepare()

                    } else {
                        toast("获取不到sdcard权限")
                    }
                },
                {
                    toast("需要读取sdcard权限")
                    finish()
                }
        )
    }

    private fun prepare() {
        mPlayer = FlacPlayer()


        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).let {
            File(it, "zelda.flac")
        }

        mPlayer.open(file)

        mPlayer.callback = {current, total ->
            progress.progress = (current * 100 / total).toInt()
        }

        renderAlbum()
    }

    private fun renderPlay() {
        iv_play.setImageResource(if (mIsPlay) R.drawable.ic_pause else R.drawable.ic_play)
    }

    private fun renderAlbum() {
        mPlayer.picture?.let {
            val image = getField(it, "image") as? ByteArray
            image?.let {data->

                val bm = BitmapFactory.decodeByteArray(data, 0, data.size)
                iv_album.setImageBitmap(bm)
            }
        }

        tv_info.text = "${mPlayer.bps()}bps duration: ${mPlayer.duration()}"
    }

    private fun play() {
        if (mIsPlay) {
            return
        }
        mIsPlay = true

        mPlayer.play()
        renderPlay()
    }

    private fun pause() {
        if (!mIsPlay) {
            return
        }
        mIsPlay = false

        mPlayer.pause()
        renderPlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPlayer.stop()
    }
}