package ru.vk.shpk.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vk.shpk.api.ServerApi
import ru.vk.shpk.data.local.AppPreferences
import ru.vk.shpk.exeption.ApiError
import ru.vk.shpk.exeption.NetworkException
import ru.vk.shpk.exeption.UnknownException
import ru.vk.shpk.interfaces.MainRepository
import java.io.IOException

class MainRepositoryImpl(context: Context) : MainRepository {

    private val _checkTokenServer = MutableLiveData<Boolean>()
    val checkTokenServer: LiveData<Boolean>
        get() = _checkTokenServer
    private val preferences: AppPreferences = AppPreferences.create(context)

    override suspend fun checkToken() {
        try {
            val token = preferences.userDetails.token.toString()
            println("TOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOKEN $token")
            if (token != null) {
                println(" ЕБАНЫЙ TOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOKEN $token")
                val response = ServerApi.service.checkToken(token)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }

                val body = response.body()
                if (body != null) {
                    preferences.storeUserDetails(body)
                    _checkTokenServer.value = true
                } else {
                    preferences.clear()
                    _checkTokenServer.value = false
                }
            }

        } catch (e: IOException) {
            e.stackTrace
            throw NetworkException
        } catch (e: Exception) {
            e.stackTrace
            throw UnknownException
        }
    }

    override suspend fun exitApp() {
        preferences.clear()
    }
}