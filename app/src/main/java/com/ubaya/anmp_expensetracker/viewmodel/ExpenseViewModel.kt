package com.ubaya.anmp_expensetracker.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.anmp_expensetracker.model.Budget
import com.ubaya.anmp_expensetracker.model.Expense
import com.ubaya.anmp_expensetracker.model.ExpenseWithBudget
import com.ubaya.anmp_expensetracker.model.ExpensesDatabase
import com.ubaya.anmp_expensetracker.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class ExpenseViewModel(application: Application):AndroidViewModel(application), CoroutineScope{
    private var job = Job()
    val expenseLD = MutableLiveData<List<ExpenseWithBudget>>()
    val expenseDetailLD = MutableLiveData<Expense>()
    val expenseLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val budgetLD = MutableLiveData<List<Budget>>()
    val percentUsedLD = MutableLiveData<Int>()
    val usedBudget = MutableLiveData<Double>(0.0)
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    fun addExpense(expense: Expense){
        launch {
            try {
                val db = buildDb(getApplication())
                db.ExpenseDao().insertAll(expense)
                Log.d("Expense", "Insert sukses")
            }catch (e:Exception){
                Log.d("Expense", "Insert Gagal ${e.message} ")
            }

        }
    }

    fun Refresh(){
        loadingLD.value = true
        expenseLoadErrorLD.value = false
        launch {
            try {
                val db = ExpensesDatabase.buildDatabase(getApplication())
                expenseLD.postValue(db.ExpenseDao().selectAll())

            }catch (e: Exception){
                expenseLoadErrorLD.postValue(true)
            }finally {
                loadingLD.postValue(false)
            }
        }
    }

    fun fetch(uuid:Int){
        launch {
            val db = buildDb(getApplication())
            expenseDetailLD.postValue(db.ExpenseDao().selectExpense(uuid))
        }
    }

    fun selectBudget(){
        launch {
            val db = buildDb(getApplication())
            budgetLD.postValue(db.BudgetDao().selectAllBudget())


        }
    }

    fun checkUsedBudget(budget_id: Int){
        launch {
            val db = buildDb(getApplication())
            usedBudget.postValue(db.ExpenseDao().checkSum(budget_id))
        }
    }

    fun updateProgressBar(budget_id: Int, totalBudget:Double){
        launch {
            val db = buildDb(getApplication())
            val used = db.ExpenseDao().checkSum(budget_id)
            val percent = ((used/totalBudget)*100).toInt()
            percentUsedLD.postValue(percent)

        }
    }

}