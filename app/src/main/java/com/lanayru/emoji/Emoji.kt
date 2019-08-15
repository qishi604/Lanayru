package com.lanayru.emoji

/**
 * @author 郑齐
 * @since 2019-08-06
 * @version V1.0
 */
class Emoji {

    var codePoint: Int = 0
    var unicode: String = ""

    constructor(codePoint: Int) {
        this.codePoint = codePoint
        unicode = String(intArrayOf(codePoint), 0, 1)
    }

    constructor(unicode: String) {
        this.unicode = unicode
        codePoint = Character.codePointAt(unicode, 0)
    }
}