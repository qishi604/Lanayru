package com.lanayru.app.ui.architecture

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.lanayru.library.ui.base.BaseActivity
import com.lanayru.dao.UserDao
import com.lanayru.data.UserDataBase
import com.lanayru.extention.dp
import com.lanayru.model.User
import com.lanayru.util.execute
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 *
 * @author seven
 * @since 2018/8/3
 * @version V1.0
 */
class UserDbActivity : BaseActivity() {

    private lateinit var mTvDb: TextView

    private var mDao: UserDao? = null

    override fun render(savedInstanceState: Bundle?) {

        val root = verticalLayout {

            padding = 16.dp

            mTvDb = textView("no data") {
                gravity = Gravity.CENTER

                textSize = 16f
                setLineSpacing(1f, 1.2f)

            }.lparams(matchParent, wrapContent)

            linearLayout {
                val et = editText {
                    hint = "id,name,age"

                }.lparams(0, wrapContent) {
                    weight = 1f
                }
                button("save") {
                    onClick {
                        save(et.text.toString().trim())
                        load()
                    }
                }
            }.lparams(matchParent, wrapContent) {
                topMargin = 24.dp
            }
        }

        setContentView(root)

        mDao = UserDataBase.getInstance(this).userDao()

        load()
    }

    private fun load() {
        execute{
            loadSync()
        }
    }

    private fun loadSync() {
        mDao!!.getAll().run {
            if (isNotEmpty()) {
                val sb = StringBuilder("|     id     |       name      |      age     |")
                forEach {
                    sb.append('\n')
                    sb.append("|     ${it.id}    |       ${it.name}      |      ${it.age}     |")
                }

                runOnUiThread {
                    mTvDb.text = sb.toString()
                }
            }
        }
    }

    private fun save(s: String) {
        if (s.isEmpty() || s.indexOf(",") < 0) {
            toast("input id,name,age")
            return
        }

        val a = s.split(",")
        if (a.size >= 3) {
            User(a[0].toLongOrNull() ?: 1, a[1], a[2].toIntOrNull() ?: 0)
                    .also {
                        execute {
                            mDao!!.save(it)
                            loadSync()
                        }
                    }
        }
    }
}