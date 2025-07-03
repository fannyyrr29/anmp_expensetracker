package com.ubaya.anmp_expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.savedstate.serialization.serializers.MutableStateFlowSerializer
import com.ubaya.anmp_expensetracker.model.BudgetWithListExpenses
import com.ubaya.anmp_expensetracker.model.ExpenseDao
import com.ubaya.anmp_expensetracker.model.ExpenseWithBudget
import com.ubaya.anmp_expensetracker.model.ExpensesDatabase
import com.ubaya.anmp_expensetracker.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ReportViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val reportLD = MutableLiveData<List<BudgetWithListExpenses>>()
    val loadingLD = MutableLiveData<Boolean>()
    val reportErrorLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    fun Refresh(uuid:Int){
        loadingLD.value = true
        reportErrorLD.value = false
        launch {
            try {
                val db = buildDb(getApplication())
                reportLD.postValue(db.ReportDao().getBudgetWithExpenses(uuid))
            }catch (e:Exception){
                reportErrorLD.postValue(true)
            }finally {
                loadingLD.postValue(false)
            }
        }
    }
}