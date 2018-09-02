package com.lanayru.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *
 * @author seven
 * @since 2018/8/3
 * @version V1.0
 */

@Entity
data class User(

        @PrimaryKey()
        val id: Long,

        var name: String = "",

        var age: Int
)