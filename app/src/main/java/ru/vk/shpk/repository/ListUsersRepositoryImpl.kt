package ru.vk.shpk.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vk.shpk.api.ServerApi
import ru.vk.shpk.dto.UserBd
import ru.vk.shpk.exeption.ApiError
import ru.vk.shpk.exeption.NetworkException
import ru.vk.shpk.exeption.UnknownException
import ru.vk.shpk.interfaces.ListUsersRepository
import java.io.IOException

class ListUsersRepositoryImpl : ListUsersRepository {

    private val _listUsersBd = MutableLiveData<List<UserBd>>()
    val listUsersBd: LiveData<List<UserBd>>
        get() = _listUsersBd

    override suspend fun getListUsersOnServer() {
        try {
            val response = ServerApi.service.getListUsersOnServer()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body()
            if (body != null) {
                _listUsersBd.value = body
            }

        } catch (e: IOException) {
            e.stackTrace
            throw NetworkException
        } catch (e: Exception) {
            println("СПИИИИИИИИИИИИИИИИИИИИИИИСОК ОШИБКА3")
            e.stackTrace
            throw UnknownException
        }
    }
}