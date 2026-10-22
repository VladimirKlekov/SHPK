package ru.vk.shpk.dto

data class NewUser(
    val id: Int,
    val login: String,
    val password: String,
    val username: String,
    val status: String,
)
