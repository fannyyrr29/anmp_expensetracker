package com.ubaya.anmp_expensetracker.model

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ReportDao {
    @Transaction
    @Query("SELECT * FROM budgets WHERE user_id=:uuid")
    fun getBudgetWithExpenses(uuid:Int): List<BudgetWithListExpenses>
}