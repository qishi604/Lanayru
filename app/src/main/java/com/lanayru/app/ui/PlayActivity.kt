package com.lanayru.app.ui

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.view.KeyEvent
import com.lanayru.app.R
import com.lanayru.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_player.*

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/10/24
 *
 **/
class PlayActivity : BaseActivity() {


    private lateinit var mAudioManager: AudioManager

    override fun render(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_player)

        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        iv_play.setOnClickListener {
            togglePlay()
        }

        iv_pre.setOnClickListener {
            pre()
        }

        iv_next.setOnClickListener {
            next()
        }
    }

    private fun togglePlay() {
//        sendCommand(if (mAudioManager.isMusicActive)
//            KeyEvent.KEYCODE_MEDIA_PLAY
//        else KeyEvent.KEYCODE_MEDIA_PAUSE)

        sendCommand(KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)
    }

    private fun pre() {
        sendCommand(KeyEvent.KEYCODE_MEDIA_PREVIOUS)
    }

    private fun next() {
        sendCommand(KeyEvent.KEYCODE_MEDIA_NEXT)
    }

    private fun sendCommand(code: Int) {
        val keyEvent = KeyEvent(KeyEvent.ACTION_UP, code)
        val intent = Intent(Intent.ACTION_MEDIA_BUTTON)
        intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent)
        sendOrderedBroadcast(intent, null)

        val keyEvent2 = KeyEvent(KeyEvent.ACTION_DOWN, code)
        val intent2 = Intent(Intent.ACTION_MEDIA_BUTTON)
        intent2.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent2)
        sendOrderedBroadcast(intent2, null)
    }


}