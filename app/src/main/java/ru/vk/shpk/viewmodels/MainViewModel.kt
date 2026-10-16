package ru.vk.shpk.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vk.shpk.repository.MainRepositoryImpl

class MainViewModel(
    context: Application
) : AndroidViewModel(context) {
    private val repository = MainRepositoryImpl(context)

    init {
        viewModelScope.launch {
            repository.checkToken()
        }
    }

    fun checkToken(): LiveData<Boolean> {
        return repository.checkTokenServer
    }

    fun exitApp(){
        viewModelScope.launch {
            repository.exitApp()
        }
    }

}