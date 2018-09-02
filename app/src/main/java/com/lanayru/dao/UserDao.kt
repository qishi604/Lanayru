package com.lanayru.dao

import android.arch.persistence.room.*
import com.lanayru.model.User

/**
 *
 * @author seven
 * @since 2018/8/3
 * @version V1.0
 */
@Dao
interface UserDao {

    @Query("select * from user")
    fun getAll(): List<User>

    @Query("select * from user where id = (:id) limit 1")
    fun getByid(id: Long): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(d: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(d: List<User>)

    @Delete
    fun delete(d: User)

    @Query("delete from user where id = (:id)")
    fun delete(id: Long)
}