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

fun invokeMethod(obj: Any, m: String, p1: Any? = null): Any? {
    var cls: Class<in Any> = obj.javaClass
    while (null != cls) {

        try {
            val m = if (null != p1 ) {
                cls.getDeclaredMethod(m, p1.javaClass)

            } else {
                cls.getDeclaredMethod(m)
            }

            if (!m.isAccessible) {
                m.isAccessible = true
            }

            return if (null != p1) {
                m.invoke(obj, p1)

            } else {
                m.invoke(obj)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            // ignore and search next
        }
        try {
            cls = cls.superclass
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    return null
}