package ru.vk.shpk.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vk.shpk.data.local.AppPreferences
import ru.vk.shpk.interfaces.CheckConnection
import ru.vk.shpk.interfaces.MainRepository

class MainRepositoryImpl(context: Context) : MainRepository {

    private val _checkTokenServer = MutableLiveData<Boolean>()
    val checkTokenServer: LiveData<Boolean>
        get() = _checkTokenServer
    private val preferences: AppPreferences = AppPreferences.create(context)

    override suspend fun checkToken() {
        try {
            if (preferences.accessToken == null) {
                _checkTokenServer.value = false
            } else {
                _checkTokenServer.value = true
            }
        } catch (e: Exception) {
            println("Ошибка проверки токена ${e.message}")
        }

    }

    override suspend fun exitApp() {
        preferences.clear()
    }
}