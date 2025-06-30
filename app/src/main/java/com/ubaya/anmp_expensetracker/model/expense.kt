package com.ubaya.anmp_expensetracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "expenses",
    foreignKeys = [ForeignKey(
        entity = Budget::class,
        parentColumns = ["uuid"],
        childColumns = ["budget_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["budget_id"])]
)
data class Expense(
    @ColumnInfo(name = "tanggal")
    var tanggal: Long,

    @ColumnInfo(name = "nominal")
    var nominal: Double,

    @ColumnInfo(name = "notes")
    var notes: String,

    @ColumnInfo(name = "budget_id")
    var budget_id: Int,

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
)