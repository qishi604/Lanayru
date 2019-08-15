package com.lanayru.emoji

import android.content.Context
import android.text.Spannable
import java.util.regex.Pattern

/**
 * @author 郑齐
 * @since 2019-08-06
 * @version V1.0
 *
 */
object EmojiManager {

    val ss = "\uD83D\uDE00,\uD83D\uDE03,\uD83D\uDE04,\uD83D\uDE01,\uD83D\uDE06,\uD83D\uDE05,\uD83D\uDE02,☺️," +
            "\uD83D\uDE0A,\uD83D\uDE07,\uD83D\uDE09,\uD83D\uDE0C,\uD83D\uDE0D,\uD83D\uDE18,\uD83D\uDE17,\uD83D\uDE19," +
            "\uD83D\uDE1A,\uD83D\uDE0B,\uD83D\uDE1B,\uD83D\uDE1D,\uD83D\uDE1C,\uD83D\uDE0E,\uD83D\uDE0F,\uD83D\uDE12," +
            "\uD83D\uDE1E,\uD83D\uDE14,\uD83D\uDE1F,\uD83D\uDE15,\uD83D\uDE23,\uD83D\uDE16,\uD83D\uDE2B,\uD83D\uDE29," +
            "\uD83D\uDE22,\uD83D\uDE2D,\uD83D\uDE24,\uD83D\uDE20,\uD83D\uDE21,\uD83D\uDE33,\uD83D\uDE31,\uD83D\uDE28," +
            "\uD83D\uDE30,\uD83D\uDE25,\uD83D\uDE13,\uD83D\uDE36,\uD83D\uDE10,\uD83D\uDE11,\uD83D\uDE2C,\uD83D\uDE2F," +
            "\uD83D\uDE26,\uD83D\uDE27,\uD83D\uDE2E,\uD83D\uDE32,\uD83D\uDE34,\uD83D\uDE2A,\uD83D\uDE35,\uD83D\uDE37," +
            "\uD83D\uDE08,\uD83D\uDC7F,\uD83D\uDC79,\uD83D\uDC7A,\uD83D\uDCA9,\uD83D\uDC7B,\uD83D\uDC80,\uD83D\uDC7D," +
            "\uD83C\uDF83,\uD83D\uDE4C,\uD83D\uDC4F,\uD83D\uDC4D,\uD83D\uDC4E,\uD83D\uDC4A,✊,\uD83D\uDC4C," +
            "\uD83D\uDC48,\uD83D\uDC49,\uD83D\uDC46,\uD83D\uDC47,✋,\uD83D\uDC4B,\uD83D\uDCAA,\uD83D\uDE4F," +
            "\uD83D\uDC84,\uD83D\uDC8B,\uD83D\uDC44,\uD83D\uDC45,\uD83D\uDC42,\uD83D\uDC43,\uD83D\uDC63,\uD83D\uDC40," +
            "\uD83D\uDC76,\uD83D\uDC67,\uD83D\uDC66,\uD83D\uDC69,\uD83D\uDC68,\uD83D\uDC75,\uD83D\uDC72,\uD83D\uDC73," +
            "\uD83D\uDC6E,️️\uD83D\uDC77️️,\uD83D\uDC82️,\uD83D\uDC78,\uD83D\uDC70,\uD83C\uDF85,\uD83D\uDC7C,\uD83D\uDE47," +
            "️️\uD83D\uDC81️,\uD83D\uDC87,\uD83D\uDC86,\uD83D\uDC6F,\uD83D\uDEB6,️\uD83C\uDFC3️,\uD83D\uDC91,\uD83D\uDC6A," +
            "\uD83D\uDC83,\uD83D\uDC85,\uD83D\uDC5A,\uD83D\uDC55,\uD83D\uDC56,\uD83D\uDC54,\uD83D\uDC57,\uD83D\uDC59," +
            "\uD83D\uDC58,\uD83D\uDC60,\uD83D\uDC61,\uD83D\uDC62,\uD83D\uDC5E,\uD83D\uDC5F,\uD83C\uDFA9,\uD83D\uDC52," +
            "\uD83C\uDF93,\uD83D\uDC51,\uD83D\uDC8D,\uD83D\uDC5D,\uD83D\uDC5B,\uD83D\uDC5C,\uD83D\uDCBC,\uD83C\uDF92," +
            "\uD83D\uDC53,\uD83C\uDF02"

    lateinit var EMOJI_PATTERN: Pattern

    lateinit var mMap: HashMap<String, Emoji>
    lateinit var mCodePointMap: HashMap<Int, Emoji>


    fun install() {
        loadEmoji()
    }

    private fun loadEmoji() {
        var patternBuilder = StringBuilder()
        var array = ss.split(",")
        val size = array.size * 4 / 3
        mMap = HashMap(size)
        mCodePointMap = HashMap(size)
        for (i in 0 until array.size) {
            val s = array[i]
            var em = Emoji(i)
            mMap[em.unicode] = em
            mCodePointMap.put(em.codePoint, em)
            if (i > 0) {
                patternBuilder.append("|")
            }
            // Pattern.quote 的作用是把 s 当成字面量（特殊符号会被转义成普通字符）
            // 举个栗子，比如搜索的时候，对输入框进行正则匹配，可以 val pattern = Pattern.compile(Pattern.quote(sInput))
            patternBuilder.append(Pattern.quote(s))
        }

        EMOJI_PATTERN = Pattern.compile(patternBuilder.toString())
    }

    fun findEmojisPattern(s: CharSequence): List<EmojiRange> {
        val list = ArrayList<EmojiRange>()
        var matcher = EMOJI_PATTERN.matcher(s)

        while (matcher.find()) {
            val found = findEmoji(s.subSequence(matcher.start(), matcher.end()))

            if (null != found) {
                val range = EmojiRange(matcher.start(), matcher.end(), found)
                list.add(range)
            }
        }

        return list
    }

    fun findEmojisCodePoint(s: CharSequence): List<IntRange> {
        val list = ArrayList<IntRange>()

        var start = 0
        val length = s.length
        while (start < length) {
            val codePoint = Character.codePointAt(s, start)
            var charCount = Character.charCount(codePoint)

            var range = IntRange(start, start + charCount)

            if (isEmoji(codePoint)) {
                list.add(range)
            }

            start += charCount
        }

        return list
    }

    fun findEmoji(candidate: CharSequence): Emoji? {
        // We need to call toString on the candidate, since the emojiMap may not find the requested entry otherwise, because the type is different.
        return mMap.get(candidate.toString())
    }

    fun isEmoji(codePoint: Int) : Boolean {
        return mCodePointMap[codePoint] != null
    }

    fun replaceWithImages(context: Context, text: Spannable, emojiSize: Float, defaultEmojiSize: Float) {
        emojiReplacer.replaceWithImages(context, text, emojiSize, defaultEmojiSize, DEFAULT_EMOJI_REPLACER)
    }

    private val DEFAULT_EMOJI_REPLACER = EmojiReplacer { context, text, emojiSize, defaultEmojiSize, fallback ->
        val existingSpans = text.getSpans(0, text.length, EmojiSpan::class.java)
        val existingSpanPositions = ArrayList<Int>(existingSpans.size)

        val size = existingSpans.size
        //noinspection ForLoopReplaceableByForEach
        for (i in 0 until size) {
            existingSpanPositions.add(text.getSpanStart(existingSpans[i]))
        }

        val findAllEmojis = findEmojisPattern(text)

        //noinspection ForLoopReplaceableByForEach
        for (i in findAllEmojis.indices) {
            val location = findAllEmojis.get(i)

            if (!existingSpanPositions.contains(location.start)) {
                text.setSpan(EmojiSpan(context, location.emoji, emojiSize),
                        location.start, location.end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
    }

    private var emojiReplacer: EmojiReplacer = DEFAULT_EMOJI_REPLACER

}