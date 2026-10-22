package ru.vk.shpk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.vk.shpk.api.ServerApi
import ru.vk.shpk.data.local.AppPreferences
import ru.vk.shpk.databinding.ActivityAppBinding
import ru.vk.shpk.exeption.ApiError

class AppActivity : AppCompatActivity() {
    private var _binding: ActivityAppBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")
    private lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = AppPreferences.create(applicationContext)

        lifecycleScope.launch {
            checkTokenValidity()
        }
    }

    suspend fun checkTokenValidity() {
        val token = preferences.userDetails.token
        if (token != null) {
            try {
                val response = ServerApi.service.checkTokenOnServer(token)
                if (!response.isSuccessful) {
                    preferences.clear()
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body()
                if (body != null) {
                    preferences.clear()
                    preferences.storeUserDetails(body)
                }
            } catch (e: Exception) {
                println(e.message)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}