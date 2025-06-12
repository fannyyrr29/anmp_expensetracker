package com.ubaya.anmp_expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class budget(
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "nominal")
    var nominal:Double,
    @ColumnInfo(name = "user_id")
    var user_id:Int,
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0

)