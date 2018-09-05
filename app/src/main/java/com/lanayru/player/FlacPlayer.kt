package com.lanayru.player

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import com.lanayru.util.Logs
import org.kc7bfi.jflac.FLACDecoder
import org.kc7bfi.jflac.io.RandomFileInputStream
import org.kc7bfi.jflac.metadata.Picture
import org.kc7bfi.jflac.metadata.SeekTable
import org.kc7bfi.jflac.metadata.StreamInfo
import org.kc7bfi.jflac.util.ByteData
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/4
 *
 **/
class FlacPlayer {

    lateinit var decoder: FLACDecoder

    lateinit var mAudioTrack: AudioTrack

    var fileLength = 0L

    lateinit var streamInfo: StreamInfo

    lateinit var seekTable: SeekTable

     var picture: Picture? = null

    var byteData = ByteData(0)

    var isPlaying = false
    var currentByte: Long = 0
    var totalFrameBytes: Long = 0

    var mPlayThread: Thread?= null

    var callback: ((current: Long, total: Long)->Unit) ? = null

    fun open(file: File) {
        fileLength = file.length()
        decoder = FLACDecoder(RandomFileInputStream(RandomAccessFile(file, "r")))
        parseMetadata()
        totalFrameBytes = streamInfo.totalSamples * streamInfo.channels * streamInfo.bitsPerSample / 8

        val rate = streamInfo.sampleRate
        val channel = AudioFormat.CHANNEL_OUT_STEREO
        val bit = AudioFormat.ENCODING_PCM_16BIT
        val bufSize = AudioTrack.getMinBufferSize(rate, channel, bit) * 2

        mAudioTrack = AudioTrack(AudioManager.STREAM_MUSIC, rate, channel, bit, bufSize, AudioTrack.MODE_STREAM)
    }

    private fun parseMetadata() {
        val array = decoder.readMetadata()
        array?.forEach {
            when (it) {
                is StreamInfo -> {
                    streamInfo = it
                }

                is SeekTable-> {
                    seekTable = it
                }
                is Picture-> {
                    picture = it
                }
            }
        }
    }

    private fun decode(): ByteData? {
        try {
            val readFrame = decoder.readNextFrame()
            byteData = decoder.decodeFrame(readFrame, byteData)
            return byteData
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun bps() = streamInfo.sampleRate * streamInfo.bitsPerSample * streamInfo.channels

    fun duration(): Long {
        // 比特率(码率 bitRate 单位 bit/s) = 采样频率 (sampleRate) * 量化精度 (bitsPerSample)
        // 总时长(s) = 文件长度（byte）* 8 / return 比特率 fileLength * 8 / bps() ---- 这个算法有点问题，对于flac来说，因为有其他信息，如图片，导致时长比真正时长长)

        // 经过测试，这个才是正确的方法。所有的样本 / 每秒采样数 = 所需采样时间（时长）
        return streamInfo.totalSamples / streamInfo.sampleRate
    }

    fun play() {
        mAudioTrack.play()
        mPlayThread = Thread(runnable)
        mPlayThread!!.start()
    }

    val runnable = Runnable {
        isPlaying = true
        while (currentByte < totalFrameBytes) {
            if (!isPlaying) {
                mAudioTrack.stop()
                return@Runnable
            }
            try {

                var buf = decode()
                buf?.data?.let {
                    val len = it.size
                    mAudioTrack.write(it, 0, len)

                    currentByte += len

                    Logs.i("current: $currentByte, total: $totalFrameBytes")
                    callback?.invoke(currentByte, totalFrameBytes)
                }
            } catch (e: Exception) {
                mAudioTrack.stop()
                if (e is ArrayIndexOutOfBoundsException) {

                }
                e.printStackTrace()
            }
        }
        mAudioTrack.stop()

    }

    fun pause() {
        isPlaying = false
        mAudioTrack.pause()
    }

    fun stop() {
        mAudioTrack.stop()
        mAudioTrack.release()
    }
}