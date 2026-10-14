package ru.vk.shpk.repository

import android.content.Context
import ru.vk.shpk.api.ServerApi
import ru.vk.shpk.data.local.AppPreferences
import ru.vk.shpk.dto.User
import ru.vk.shpk.exeption.ApiError
import ru.vk.shpk.interfaces.AuthRepository

class AuthRepositoryImpl(context: Context) : AuthRepository {
    private val preferences: AppPreferences = AppPreferences.create(context)


    override suspend fun singIn(user: User) {
        try {
            val response = ServerApi.service.singIn(user)
            if (!response.isSuccessful) {
                println("ХУУУУУУУУУУУУУУУУУУУУУУУУЙНЯ")
                throw ApiError(response.code(), response.message())
                println(response.code().toString())
                println(response.message().toString())
            }
            val body = response.body()
            println("ОООООООООООООООООООООООООООООТВЕТ ${body.toString()}")


        } catch (e: Exception) {
            println("ХУУУУУУУУУУУУУУУЙНЯ ПОЛНАЯ ${e.message}")
        }
    }

    override suspend fun getHello() {
        try {
            val response = ServerApi.service.getHello()
            if (!response.isSuccessful) {
                println("ХУУУУУУУУУУУУУУУУУУУУУУУУЙНЯ ХЭЛЛОУ")
                throw ApiError(response.code(), response.message())
            }
            val body = response.body()
            println("ОООООООООООООООООООООООООООООТВЕТ ХЭЛЛОУ ${body.toString()}")
//            return body

        } catch (e: Exception) {
            println("ХУУУУУУУУУУУУУУУЙНЯ ПОЛНАЯ ХЭЛЛОУ ${e.message}")
        }
    }
}