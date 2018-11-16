package com.lanayru.app.ui.custom

import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.view.RulerView

/**
 *
 * @version 1.0
 * @author seven
 * @since 2018/9/19
 *
 **/
class RuleViewActivity: BaseActivity() {

    private lateinit var mRoot: FrameLayout


    override fun renderToolbar() {
//        super.renderToolbar()
    }

    override fun render(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mRoot = FrameLayout(_this).apply {
            addView(RulerView(_this))
        }
        setContentView(mRoot)

//        val mPermission = RxPermissions(_this)
//        mPermission.request(Manifest.permission.CAMERA)
//                .subscribe {
//                    if (it) {
//                        mRoot.addView(CameraView(_this), 0)
//                    }
//                }
    }
}