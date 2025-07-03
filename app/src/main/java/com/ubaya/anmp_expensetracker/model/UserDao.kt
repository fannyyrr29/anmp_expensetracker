package com.ubaya.anmp_expensetracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg user: User)

    @Query("SELECT * FROM users WHERE username= :username AND password= :password")
    fun checkUser(username:String, password:String): User

    @Query("SELECT * FROM users WHERE username= :username")
    fun checkUsername(username:String): User

    @Update
    fun updateUser(user: User)
}