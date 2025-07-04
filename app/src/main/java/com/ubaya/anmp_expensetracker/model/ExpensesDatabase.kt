package com.ubaya.anmp_expensetracker.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ubaya.anmp_expensetracker.util.DB_NAME


@Database(entities = arrayOf(User::class, Budget::class, Expense::class), version = 1)
abstract class ExpensesDatabase:RoomDatabase() {
    abstract fun userDao():UserDao
    abstract fun BudgetDao():BudgetDao
    abstract fun ExpenseDao():ExpenseDao
    abstract fun ReportDao():ReportDao

    companion object{
        @Volatile private var instance: ExpensesDatabase ?= null
        private val LOCK = Any()


        fun buildDatabase(context:Context) = Room.databaseBuilder(
            context.applicationContext,
            ExpensesDatabase::class.java,
            DB_NAME).build()

        operator fun invoke(context:Context) {
            if(instance == null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }


    }
}