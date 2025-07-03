package com.ubaya.anmp_expensetracker.viewmodel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.anmp_expensetracker.model.Budget
import com.ubaya.anmp_expensetracker.model.ExpensesDatabase
import com.ubaya.anmp_expensetracker.util.buildDb
import com.ubaya.anmp_expensetracker.util.formatRupiah
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class BudgetViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val budgetLD = MutableLiveData<List<Budget>>()
    val detailBudgetLD = MutableLiveData<Budget>()
    val budgetLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val insertSuccess = MutableLiveData<Boolean>()

    val toastMessage = MutableLiveData<String>()

    private var job = Job()


    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    fun AddBudget(budget: Budget){
        launch {
            val db = buildDb(getApplication())
            db.BudgetDao().insertAll(budget)
            insertSuccess.postValue(true)
        }
    }

    fun Refresh(){
        loadingLD.value = true
        budgetLoadErrorLD.value = false
        launch {
            try {
                val db = ExpensesDatabase.buildDatabase(getApplication())
                val uuid = getUuidFromPref()?.toIntOrNull() ?: 0
                budgetLD.postValue(db.BudgetDao().selectAllBudget(uuid))

            }catch (e: Exception){
                budgetLoadErrorLD.postValue(true)
            }finally {
                loadingLD.postValue(false)
            }
        }

    }
    fun fetch(uuid:Int){
        launch {
            val db = buildDb(getApplication())
            detailBudgetLD.postValue(db.BudgetDao().selectBudget(uuid))
        }
    }

    fun update(name:String, nominal:Double, user_id:Int, uuid:Int)
    {
        launch {
            val db = buildDb(getApplication())
            //cek apakah budget yang diset lebih kecil dari expense
            val totalExpense = db.BudgetDao().getTotalExpenseForBudget(uuid) ?: 0.0

            if (nominal < totalExpense) {
                // Gagal update, budget terlalu kecil
                toastMessage.postValue("Budget tidak boleh lebih kecil dari total pengeluaran!, Minimal Rp."+ formatRupiah(totalExpense))
                return@launch
            }
            else{
                db.BudgetDao().update(name, nominal, user_id, uuid)
                toastMessage.postValue("Budget berhasil diupdate")
            }


        }
    }
    private fun getUuidFromPref(): String {
        val pref = getApplication<Application>().getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        return pref.getString("uuid", null) ?: ""
    }


}