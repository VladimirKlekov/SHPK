package ru.vk.shpk.interfaces

interface ListUsersRepository {
    suspend fun getListUsersOnServer()
    suspend fun deleteUserOnServer(id:Int)
}