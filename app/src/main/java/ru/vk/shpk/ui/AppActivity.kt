package ru.vk.shpk.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.vk.shpk.checkinternet.NetworkConnectionLiveData
import ru.vk.shpk.databinding.ActivityAppBinding
import ru.vk.shpk.interfaces.CheckConnection

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