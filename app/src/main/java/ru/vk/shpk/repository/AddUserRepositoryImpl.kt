package ru.vk.shpk.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.vk.shpk.api.ServerApi
import ru.vk.shpk.dto.NewUser
import ru.vk.shpk.exeption.ApiError
import ru.vk.shpk.exeption.NetworkException
import ru.vk.shpk.exeption.UnknownException
import ru.vk.shpk.interfaces.AddUserRepository
import java.io.IOException

class AddUserRepositoryImpl: AddUserRepository {

    private val _serverResponse = MutableLiveData<String>()
    val serverResponse: LiveData<String>
        get() = _serverResponse

    override suspend fun addUserOnServer(user: NewUser) {
        try {
            val response = ServerApi.service.addUserOnServer(user)
            println("Репозиторий мооооооооооооодель Запрс $response")
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
                println("Репозиторий мооооооооооооодель Апи ошибка")
            }
            val body = response.body()
            if (body != null) {
                _serverResponse.value = response.message()
            }

        } catch (e: IOException) {
            _serverResponse.value = e.message
            println("Репозиторий мооооооооооооодель IOException ошибка ${e.message}")
            throw NetworkException
        } catch (e: Exception) {
            println("Репозиторий мооооооооооооодель Exception ошибка ${e.message}")
            _serverResponse.value = e.message
            throw UnknownException
        }
    }
}