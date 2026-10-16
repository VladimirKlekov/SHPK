package ru.vk.shpk.repository

import android.content.Context
import ru.vk.shpk.api.ServerApi
import ru.vk.shpk.data.local.AppPreferences
import ru.vk.shpk.dto.User
import ru.vk.shpk.exeption.ApiError
import ru.vk.shpk.exeption.NetworkException
import ru.vk.shpk.exeption.UnknownException
import ru.vk.shpk.interfaces.AuthRepository
import java.io.IOException

class AuthRepositoryImpl(context: Context) : AuthRepository {
    private val preferences: AppPreferences = AppPreferences.create(context)

    override suspend fun singIn(user: User) {
        try {
            val response = ServerApi.service.singIn(user)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

            val body = response.body()
            preferences.storeAccessToken(body.toString())

        } catch (e: IOException) {
            throw NetworkException
        } catch (e: Exception) {
            throw UnknownException
        }
    }
}