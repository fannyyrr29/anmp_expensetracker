package com.ubaya.anmp_expensetracker.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg expense: Expense)

    @Query("SELECT e.* FROM expenses AS e INNER JOIN budgets AS b ON e.budget_id = b.uuid WHERE b.user_id=:user_id")
    fun selectAll(user_id:Int): List<ExpenseWithBudget>

    @Query("SELECT * FROM expenses WHERE uuid=:uuid")
    fun selectExpense(uuid: Int):Expense

    @Query("UPDATE expenses SET tanggal=:tanggal, nominal=:nominal, budget_id=:budget_id, notes=:note WHERE uuid=:uuid")
    fun update(tanggal:String, nominal:Double, budget_id:Int, note:String, uuid:Int)

    @Query("SELECT sum(nominal) FROM expenses WHERE budget_id=:budget_id")
    fun checkSum(budget_id:Int):Double
}