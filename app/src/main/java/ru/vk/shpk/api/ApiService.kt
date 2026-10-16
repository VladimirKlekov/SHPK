package ru.vk.shpk.api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.vk.shpk.dto.Api
import ru.vk.shpk.dto.Token
import ru.vk.shpk.dto.User


private const val BASE_URL = "http://192.168.0.59:8080"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ServerApiService {

    @POST("/login")
    suspend fun singIn(@Body user:User): Response<Token>

    @GET("/test")
    suspend fun testServer(): Response<Boolean>

    @GET("/")
    suspend fun getHello(): Response<Api>


}

object ServerApi {
    val service: ServerApiService by lazy {
        retrofit.create(ServerApiService::class.java)
    }
}