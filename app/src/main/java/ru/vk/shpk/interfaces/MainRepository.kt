package ru.vk.shpk.interfaces

interface MainRepository {
    suspend fun checkToken()
}