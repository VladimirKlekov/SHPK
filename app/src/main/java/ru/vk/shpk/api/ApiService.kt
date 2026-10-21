package ru.vk.shpk.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import ru.vk.shpk.dto.UserDetails
import ru.vk.shpk.dto.SingInUser


private const val BASE_URL = "http://192.168.0.59:8080"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ServerApiService {

    @POST("/users/login")
    suspend fun singIn(@Body user: SingInUser): Response<UserDetails>

    @POST("users/{token}")
    suspend fun checkToken(@Path("token") token:String): Response<UserDetails>
}

object ServerApi {
    val service: ServerApiService by lazy {
        retrofit.create(ServerApiService::class.java)
    }
}