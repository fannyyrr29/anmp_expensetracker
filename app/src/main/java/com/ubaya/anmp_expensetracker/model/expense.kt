package com.ubaya.anmp_expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class Expense(
    @ColumnInfo("tanggal")
    var tanggal:Long,
    @ColumnInfo("nominal")
    var nominal:Double,
    @ColumnInfo("notes")
    var notes:String,
    @ColumnInfo("budget_id")
    var budget_id:Int,
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0
)