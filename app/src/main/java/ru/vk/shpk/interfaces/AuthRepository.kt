package ru.vk.shpk.interfaces

import ru.vk.shpk.dto.SingInUser

interface AuthRepository {
    suspend fun singIn(user: SingInUser)
}