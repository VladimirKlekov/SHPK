package ru.vk.shpk.interfaces

interface ListUsersRepository {
    suspend fun getListUsersOnServer()
}