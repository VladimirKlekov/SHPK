package ru.vk.shpk.interfaces

import ru.vk.shpk.dto.User

interface AuthRepository {
    suspend fun singIn(user: User)
}