package com.ubaya.anmp_expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.anmp_expensetracker.model.ExpensesDatabase
import com.ubaya.anmp_expensetracker.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignInViewModel(application: Application):AndroidViewModel(application), CoroutineScope {
    val userLogin = MutableLiveData<User>()
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun checkLogin(username:String, password:String){
        launch {
            val db = ExpensesDatabase.buildDatabase(getApplication())
            userLogin.postValue(db.userDao().checkUser(username, password))
        }
    }
}