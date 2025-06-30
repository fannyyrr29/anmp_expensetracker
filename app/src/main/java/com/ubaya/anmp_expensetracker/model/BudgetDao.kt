package com.ubaya.anmp_expensetracker.model

import android.content.IntentSender.SendIntentException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg budget: Budget)

    @Query("SELECT * FROM budgets")
    fun selectAllBudget():List<Budget>

    @Query("SELECT * FROM budgets WHERE uuid=:uuid")
    fun selectBudget(uuid:Int):Budget

    @Query("UPDATE budgets SET name=:name, nominal=:nominal, user_id=:user_id WHERE uuid=:uuid")
    fun update(name:String, nominal:Double, user_id:Int, uuid:Int)

    @Delete
    fun deleteBudget(budget: Budget)
}