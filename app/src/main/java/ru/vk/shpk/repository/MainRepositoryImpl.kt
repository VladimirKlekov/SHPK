package ru.vk.shpk.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vk.shpk.data.local.AppPreferences
import ru.vk.shpk.interfaces.MainRepository

class MainRepositoryImpl(context: Context) : MainRepository {

    private val _checkTokenServer = MutableLiveData<Boolean>()
    val checkTokenServer: LiveData<Boolean>
        get() = _checkTokenServer

    private val _checkStatus = MutableLiveData<String>()
    val checkStatus: LiveData<String>
        get() = _checkStatus
    private val preferences: AppPreferences = AppPreferences.create(context)

    override suspend fun checkToken() {
        val token = preferences.userDetails.token
        if (token != null) {
            _checkTokenServer.value = true
        } else {
            _checkTokenServer.value = false
        }

        val status = preferences.userDetails.status
        if(status != null){
            _checkStatus.value = status
        }

    }

    override suspend fun exitApp() {
        preferences.clear()
    }
}