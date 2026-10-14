package ru.vk.shpk.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vk.shpk.dto.User
import ru.vk.shpk.repository.AuthRepositoryImpl

class AuthViewModel(context: Application): AndroidViewModel(context) {
    private val repository = AuthRepositoryImpl(context)

    fun singIn(user: User){
        viewModelScope.launch {
            repository.getHello()
            repository.singIn(user)
        }

    }
}