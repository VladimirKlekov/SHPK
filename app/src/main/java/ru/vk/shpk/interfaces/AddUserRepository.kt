package ru.vk.shpk.interfaces

import ru.vk.shpk.dto.NewUser

interface AddUserRepository {
    suspend fun addUserOnServer(user: NewUser)
}