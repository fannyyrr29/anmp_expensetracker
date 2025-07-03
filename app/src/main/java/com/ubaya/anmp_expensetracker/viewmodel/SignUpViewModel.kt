package com.ubaya.anmp_expensetracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ubaya.anmp_expensetracker.model.ExpensesDatabase
import com.ubaya.anmp_expensetracker.model.User
import com.ubaya.anmp_expensetracker.model.UserDao
import com.ubaya.anmp_expensetracker.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class SignUpViewModel(application:Application):AndroidViewModel(application), CoroutineScope{
    private val job = Job()
    val toastMessage = MutableLiveData<String>()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO


    fun createAccount(user:User){
        launch{
            val db = buildDb(getApplication())
            val usercheck = db.userDao().checkUsername(user.username)
            if (usercheck!=null){
                toastMessage.postValue("Username sudah dipakai!")
            }
            else{
                toastMessage.postValue("Akun berhasil dibuat!")
                db.userDao().insertAll(user)
            }

        }
    }
}