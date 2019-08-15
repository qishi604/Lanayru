package com.lanayru.app.xml

import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.GsonUtils
import com.google.gson.reflect.TypeToken
import com.lanayru.model.Emoji
import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.FileReader
import javax.xml.parsers.DocumentBuilderFactory



/**
 * @author 郑齐
 * @since 2019-08-08
 * @version V1.0
 *
 */
class EmojiLoader {

    @Test
    fun load() {
        var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        var inputStream = FileInputStream("/Users/seven/Desktop/emoji.xml")
        var document = builder.parse(inputStream)
        var nodeList = document.getElementsByTagName("exp")


        if (nodeList.length > 0) {
            val list = ArrayList<Emoji>(400)
            for (i in 0 until nodeList.length) {
                val node = nodeList.item(i)

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

            var content = GsonUtils.toJson(list)
            FileIOUtils.writeFileFromString(File("/Users/seven/Desktop/emoji-unicode.json"), content)
        }
    }

    data class TmpEmoji(var name: String, var desc: String)

    @Test
    fun loadConfig() {
        val dir = "/Users/seven/Desktop/emoji-unicode/"
        val reader = FileReader("${dir}emoji-part.json")
        val listType = object : TypeToken<List<Emoji>>(){}.type
        val list = GsonUtils.fromJson<List<Emoji>>(reader, listType)

        val sb = StringBuilder()
        val resultList = ArrayList<TmpEmoji>(list.size)
        for (d in list) {
            var unicode = Integer.parseInt(d.unified, 16)
            var unified = String(Character.toChars(unicode))

            resultList.add(TmpEmoji(d.name, unified))

            val s = "{\"name\":\"${d.name}\",\"desc\":\"${unified}\"},"
            println(s)
        }

        var content = GsonUtils.toJson(resultList)
        FileIOUtils.writeFileFromString(File("${dir}emoji-unicode-final.json"), content)
    }

    fun loadAll() : ArrayList<Emoji>? {
        var builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        var inputStream = FileInputStream("~/Desktop/emoji.xml")
        var document = builder.parse(inputStream)
        var nodeList = document.getElementsByTagName("exp")

        if (nodeList.length > 0) {
            var exppkg = nodeList.item(0)
            var nodes = exppkg.childNodes

            val list = ArrayList<Emoji>(128)
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