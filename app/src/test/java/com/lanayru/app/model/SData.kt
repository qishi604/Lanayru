package com.lanayru.app.model

class SData {

    var d: String? = null

    override fun equals(other: Any?): Boolean {
        if (null == other) {
            return false
        }

        if (other is String) {
            return other == d
        }
        return super.equals(other)
    }
}