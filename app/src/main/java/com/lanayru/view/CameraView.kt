package com.lanayru.view

import android.content.Context
import android.hardware.Camera
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import org.jetbrains.anko.toast

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/20
 *
 **/
class CameraView: FrameLayout, SurfaceHolder.Callback {


    private val mSurfaceView: SurfaceView = SurfaceView(context)

    private var mHolder: SurfaceHolder? = null

    private var mCamera: Camera? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        addView(mSurfaceView)

        mHolder = mSurfaceView.holder
        mHolder?.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        mCamera?.let {
            val parameters = it.parameters
            val sizes = it.parameters.supportedPreviewSizes

            parameters.setPreviewSize(sizes[0].width, sizes[1].height)
            requestLayout()
            it.parameters = parameters

            it.startPreview()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        release()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
    }

    fun isOpen(): Boolean = null != mCamera

    fun openCamera() : Boolean {
        try {
            release()
            mCamera = Camera.open()
            requestLayout()
            mCamera!!.setPreviewDisplay(mHolder)
            mCamera!!.setDisplayOrientation(90)

            mCamera!!.startPreview()


        } catch (e: Exception) {
            context.toast("failed to open camera")
            e.printStackTrace()
        }

        return null != mCamera
    }

    private fun release() {
        mCamera?.stopPreview()
        mCamera?.release()
        mCamera = null
    }

    override fun onVisibilityChanged(changedView: View?, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE) {
            openCamera()
        } else {
            release()
        }
    }
}