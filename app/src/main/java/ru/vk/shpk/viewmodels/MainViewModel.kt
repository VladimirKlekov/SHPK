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
            try {
                repository.checkToken()
            }catch (e:Exception){
                println("Вью модель ошибка ${e.message}")
            }

        }
    }

    fun checkToken(): LiveData<Boolean> {
        return repository.checkTokenServer
    }

    fun checkStatus(): LiveData<String> {
        return repository.checkStatus
    }

    fun exitApp(){
        viewModelScope.launch {
            repository.exitApp()
        }
    }

}