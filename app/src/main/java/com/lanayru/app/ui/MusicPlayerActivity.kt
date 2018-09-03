package com.lanayru.app.ui

import android.media.MediaPlayer
import android.os.Bundle
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.util.Files
import kotlinx.android.synthetic.main.activity_music_player.*
import java.io.File

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/3
 *
 **/
class MusicPlayerActivity: BaseActivity() {

    private lateinit var mMediaPlayer: MediaPlayer

    private var mIsPlay = false

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_music_player)

        prepare()

        iv_play.setOnClickListener {
            mIsPlay = !mIsPlay
            if (mIsPlay) {
                pause()
            } else {
                play()
            }
        }
    }

    private fun prepare() {
        mMediaPlayer = MediaPlayer()
        mMediaPlayer.setOnCompletionListener {
            mIsPlay = mMediaPlayer.isPlaying
            renderPlay()
        }

        val file = Files.getPublicDir("music").let {
            File(it, "zelda.flac")
        }

        mMediaPlayer.setDataSource(file.path)

        mMediaPlayer.prepareAsync()
    }

    private fun renderPlay() {
        iv_play.setImageResource(if (mIsPlay) R.drawable.ic_pause else R.drawable.ic_play)
    }

    private fun play() {
        if (mIsPlay) {
            return
        }

        mMediaPlayer.start()
        renderPlay()
    }

    private fun pause() {
        if (!mIsPlay) {
            return
        }
        mMediaPlayer.pause()
        renderPlay()
    }



    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer.release()
    }
}