package com.lanayru.app.ui

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.extention.dp
import org.jetbrains.anko.editText
import org.jetbrains.anko.linearLayout

/**
 * @author 郑齐
 * @since 2019/5/29
 * @version V1.0
 *
 */
class KeyboardTestActivity: BaseActivity() {

    override fun render(savedInstanceState: Bundle?) {
        linearLayout {
            val et =  editText {
                hint = "say something..."

                setImeActionLabel("Send", EditorInfo.IME_ACTION_SEND)
                textSize = 14f
                maxLines = 4

            }

            et.layoutParams.width = 200.dp

        }
    }
}