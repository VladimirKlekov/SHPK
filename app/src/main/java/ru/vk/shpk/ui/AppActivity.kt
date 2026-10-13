package ru.vk.shpk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.vk.shpk.databinding.ActivityAppBinding

class AppActivity: AppCompatActivity() {
    private var _binding: ActivityAppBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}