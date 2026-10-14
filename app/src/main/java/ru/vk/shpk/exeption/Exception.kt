package ru.vk.shpk.exeption

sealed class AppError(private val code: String) : RuntimeException()

class ApiError(val status: Int, code: String) : AppError(code)

object NetworkException : AppError("error_network")

object UnknownException : AppError("error_unknown")