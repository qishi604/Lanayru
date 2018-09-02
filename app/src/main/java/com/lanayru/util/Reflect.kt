package com.lanayru.util

/**
 *
 * @author seven
 * @since 2018/7/30
 * @version V1.0
 */

fun getField(obj: Any, fieldName: String): Any? {
    var cls: Class<in Any> = obj.javaClass
    while (null != cls) {

        try {
            val field = cls.getDeclaredField(fieldName)

            if (!field.isAccessible) {
                field.isAccessible = true
            }
            return field.get(obj)

        } catch (e: NoSuchFieldException) {
            // ignore and search next
        }
        cls = cls.superclass
    }
    return null
}

fun setField(obj: Any, fieldName: String, value: Any) {
    var cls: Class<in Any> = obj.javaClass
    while (null != cls) {

        try {
            val field = cls.getDeclaredField(fieldName)

            if (!field.isAccessible) {
                field.isAccessible = true
            }
            field.set(obj, value)
            return

        } catch (e: NoSuchFieldException) {
            // ignore and search next
        }
        cls = cls.superclass
    }
}