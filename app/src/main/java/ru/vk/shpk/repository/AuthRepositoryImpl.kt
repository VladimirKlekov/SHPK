package ru.vk.shpk.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vk.shpk.api.ServerApi
import ru.vk.shpk.data.local.AppPreferences
import ru.vk.shpk.dto.SingInUser
import ru.vk.shpk.exeption.ApiError
import ru.vk.shpk.exeption.NetworkException
import ru.vk.shpk.exeption.UnknownException
import ru.vk.shpk.interfaces.AuthRepository
import java.io.IOException

class AuthRepositoryImpl(context: Context) : AuthRepository {
    private val preferences: AppPreferences = AppPreferences.create(context)
    private val _serverResponse = MutableLiveData<String>()
    val serverResponse: LiveData<String>
        get() = _serverResponse

    override suspend fun singIn(user: SingInUser) {
        try {
            val response = ServerApi.service.singIn(user)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body()
            if (body != null) {
                preferences.storeUserDetails(body)
                _serverResponse.value = response.message()
            }

        } catch (e: IOException) {
            _serverResponse.value = e.message
            throw NetworkException
        } catch (e: Exception) {
            _serverResponse.value = e.message
            throw UnknownException
        }
    }

}