package ru.vk.shpk.repository

import androidx.lifecycle.MutableLiveData
import ru.vk.shpk.api.ServerApi
import ru.vk.shpk.dto.UserBd
import ru.vk.shpk.exeption.ApiError
import ru.vk.shpk.exeption.NetworkException
import ru.vk.shpk.exeption.UnknownException
import ru.vk.shpk.interfaces.ListUsersRepository
import java.io.IOException

class ListUsersRepositoryImpl : ListUsersRepository {

    val listUsersBd = MutableLiveData<List<UserBd>>()

    override suspend fun getListUsersOnServer() {
        try {
            val response = ServerApi.service.getListUsersOnServer()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body()
            if (body != null) {
                listUsersBd.value = body
            }

        } catch (e: IOException) {
            e.stackTrace
            throw NetworkException
        } catch (e: Exception) {
            e.stackTrace
            throw UnknownException
        }
    }

    override suspend fun deleteUserOnServer(id: Int) {
        try {
            val response = ServerApi.service.deleteUserOnServer(id)
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }

        } catch (e: IOException) {
            e.stackTrace
            throw NetworkException
        } catch (e: Exception) {
            e.stackTrace
            throw UnknownException
        }
    }
}