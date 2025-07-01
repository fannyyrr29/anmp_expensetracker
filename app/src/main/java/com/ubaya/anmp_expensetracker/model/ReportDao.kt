package com.ubaya.anmp_expensetracker.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ReportDao {
    @Transaction
    @Query("SELECT * FROM budgets")
    fun getBudgetWithExpenses(): List<BudgetWithListExpenses>
}