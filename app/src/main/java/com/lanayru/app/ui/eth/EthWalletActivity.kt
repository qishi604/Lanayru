package com.lanayru.app.ui.eth

import android.os.Bundle
import android.widget.TextView
import com.lanayru.app.ui.base.BaseActivity
import com.lanayru.extention.dp
import com.lanayru.util.Files
import com.lanayru.util.execute
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.Web3j
import java.io.File

/**
 *
 * @author seven
 * @since 2018/8/13
 * @version V1.0
 */
class EthWalletActivity: BaseActivity(), AnkoLogger {

    private lateinit var mTvInfo: TextView

    private lateinit var mWeb3: Web3j

    override fun render(savedInstanceState: Bundle?) {
        val root = verticalLayout {
            mTvInfo = textView("info") {
                textSize = 16F
                padding = 16.dp
            }

            button("create wallet") {
                onClick { createWallet() }

            }.lparams(matchParent, wrapContent){
                topMargin = 16.dp
            }

        }

        setContentView(root)

//        mWeb3 = Web3jFactory.build(HttpService("http://192.168.7.28:8545"))
    }

    private fun createWallet() {
        val file = Files.getPublicDir("wallet")

        execute {
            val s = WalletUtils.generateNewWalletFile("123456", file, true)

//            loadCredentials(file)?.let {
//                info { it.address + "  " + it.ecKeyPair }
//            }
        }
    }

    private fun loadCredentials(file: File): Credentials? {
        return WalletUtils.loadCredentials("123456", file)
    }

    private fun w3() {
        mWeb3.blockObservable(true)
                .take(10)
                .subscribe({
                    val block = it.block
                    info {
                        " ${Thread.currentThread().name} hash: ${block.hash} parent hash: ${block.parentHash} ${block.number}"
                    }
                },{
                    error {
                        " ${Thread.currentThread().name} ${it.message}"
                    }
                })
    }

    private fun log() {
       execute {
           val res = mWeb3.ethGetBalance("0x407d73d8a49eeb85d32cf465507dd71d507100c1", {"eth"}).send()
           info { "balance: $res balance: ${res.balance}" }
       }



    }
}