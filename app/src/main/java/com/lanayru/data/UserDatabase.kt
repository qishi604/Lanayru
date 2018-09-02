package com.lanayru.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.lanayru.dao.UserDao
import com.lanayru.model.User

/**
 *
 * @author seven
 * @since 2018/8/3
 * @version V1.0
 */

@Database(entities = [
    User::class
],
        version = 1
)
abstract class UserDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private const val DEFAULT_DB = "user.db"

        @Volatile
        private var INSTANCE: UserDataBase? = null

        fun getInstance(context: Context): UserDataBase  =
                INSTANCE ?: synchronized(this) {
                    INSTANCE?: newDataBase(context, DEFAULT_DB).also { INSTANCE = it }
                }

        fun newDataBase(context: Context, db: String) = Room.databaseBuilder(context.applicationContext, UserDataBase::class.java, db)
                .build()
    }
}