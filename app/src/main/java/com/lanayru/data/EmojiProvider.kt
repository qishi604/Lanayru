package com.lanayru.data

import com.blankj.utilcode.util.Utils
import com.lanayru.model.Emoji
import javax.xml.parsers.DocumentBuilderFactory

/**
 * @author 郑齐
 * @since 2019-08-01
 * @version V1.0
 *
 */
object EmojiProvider {

    fun load() : ArrayList<Emoji>? {
        var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        var inputStream = Utils.getApp().assets.open("emoji/emoji.xml")
        var document = builder.parse(inputStream)
        var exppkgList = document.getElementsByTagName("exppkg")

        if (exppkgList.length > 0) {
            var exppkg = exppkgList.item(0)
            var nodes = exppkg.childNodes

            val list = ArrayList<Emoji>(87)
            for (i in 0 until nodes.length) {
                var node = nodes.item(i)
                if (node.nodeName == "exp") {
                    var attributes = node.attributes
                    if (null != attributes && attributes.length > 0) {
                        var name = attributes.getNamedItem("name").nodeValue
                        var other = attributes.getNamedItem("other").nodeValue
                        var pinyin = attributes.getNamedItem("pinyin").nodeValue
                        var softbank = attributes.getNamedItem("softbank").nodeValue
                        var unified = attributes.getNamedItem("unified").nodeValue
                        list.add(Emoji(name, other, pinyin, softbank, unified))
                    }
                }
            }
            return list
        }

        return null
    }


    fun loadAll() : ArrayList<Emoji>? {
        var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        var inputStream = Utils.getApp().assets.open("emoji/emoji.xml")
        var document = builder.parse(inputStream)
        var exppkgList = document.getElementsByTagName("exppkg")

        if (exppkgList.length > 0) {
            var exppkg = exppkgList.item(0)
            var nodes = exppkg.childNodes

            val list = ArrayList<Emoji>(87)
            for (i in 0 until nodes.length) {
                var node = nodes.item(i)
                if (node.nodeName == "exp") {
                    var attributes = node.attributes
                    if (null != attributes && attributes.length > 0) {
                        var name = attributes.getNamedItem("name").nodeValue
                        var other = attributes.getNamedItem("other").nodeValue
                        var pinyin = attributes.getNamedItem("pinyin").nodeValue
                        var softbank = attributes.getNamedItem("softbank").nodeValue
                        var unified = attributes.getNamedItem("unified").nodeValue
                        list.add(Emoji(name, other, pinyin, softbank, unified))
                    }
                }
            }
            return list
        }

        return null
    }


}