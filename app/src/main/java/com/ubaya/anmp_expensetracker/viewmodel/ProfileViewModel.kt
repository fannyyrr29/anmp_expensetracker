package com.ubaya.anmp_expensetracker.viewmodel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubaya.anmp_expensetracker.model.User
import com.ubaya.anmp_expensetracker.util.buildDb
import com.ubaya.anmp_expensetracker.view.ProfileListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(application:Application):AndroidViewModel(application),ProfileListener,
    CoroutineScope {
    val txtOldPassword = MutableLiveData<String>()
    val txtNewPassword = MutableLiveData<String>()
    val txtConfirmPassword = MutableLiveData<String>()
    val toastMessage = MutableLiveData<String>()
    val isSignOut= MutableLiveData<Boolean>()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    override fun changePassword() {
        if (txtOldPassword.value == ""|| txtNewPassword.value == "" || txtConfirmPassword.value == "") {
            toastMessage.value = "Semua field harus diisi"
            return
        }

        //check new dan confirm sama
        if (txtNewPassword.value != txtConfirmPassword.value){
            toastMessage.value = "Password baru dan konfirmasi tidak cocok"
            return
        }

        //check apakah old pw benar dimasukkan dengan method login
        val username = getUsernameFromPref()
        launch {
            val db = buildDb(getApplication())
            val user = db.userDao().checkUser(username, txtOldPassword.value)

            if (user == null) {
                toastMessage.postValue("Password lama salah")
            } else {
                // Semua valid, update password
                user.password = txtNewPassword.value
                db.userDao().updateUser(user)
                toastMessage.postValue("Password berhasil diganti!")
                txtOldPassword.value =""
                txtNewPassword.value = ""
                txtConfirmPassword.value = ""
            }
        }
    }

    override fun signOut() {
        val pref = getApplication<Application>().getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        pref.edit().clear().apply()
        toastMessage.value = "Berhasil logout"
        isSignOut.value = true
    }
    private fun getUsernameFromPref(): String {
        val pref = getApplication<Application>().getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        return pref.getString("username", null) ?: ""
    }

}