package com.lanayru.app.ui.album

import android.os.Bundle
import android.widget.ImageView
import com.lanayru.app.R
import com.lanayru.library.image.GlideApp
import com.lanayru.library.ui.base.BaseActivity
import org.jetbrains.anko.button
import org.jetbrains.anko.imageView
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.verticalLayout

/**
 * @author 郑齐
 * @since 2019-07-15
 * @version V1.0
 *
 */
class LoadMemActivity: BaseActivity() {

    lateinit var mIvImage: ImageView

    override fun render(savedInstanceState: Bundle?) {
        verticalLayout {
            button("1000") {
                onClick {
                    load(1000)
                }
            }

            button("10000") {
                onClick {
                    load(5000)
                }
            }

            button("40000") {
                onClick {
                    load(8000)
                }
            }

            mIvImage = imageView()
        }
    }

    private fun load(size: Int) {
        GlideApp.with(this).load(R.drawable.zelda).override(size).into(mIvImage)
    }
}