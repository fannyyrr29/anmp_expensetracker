package com.ubaya.anmp_expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @ColumnInfo(name="username")
    var username:String,
    @ColumnInfo(name = "first_name")
    var firstname:String,
    @ColumnInfo(name = "last_name")
    var lastname:String,
    @ColumnInfo(name = "password")
    var password:String,
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0

)