package ru.vk.shpk.dto

data class UserBd(
    val id: Int,
    val login: String,
    val password: String,
    val username: String,
    val status: String,
    val token: String
) {
}