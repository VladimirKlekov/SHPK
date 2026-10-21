package ru.vk.shpk.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vk.shpk.dto.SingInUser
import ru.vk.shpk.feedmodel.FeedModelState
import ru.vk.shpk.repository.AuthRepositoryImpl

class AuthViewModel(context: Application) : AndroidViewModel(context) {
    private val repository = AuthRepositoryImpl(context)

    private val _state = MutableLiveData<FeedModelState>()
    val state: MutableLiveData<FeedModelState>
        get() = _state

    fun singIn(user: SingInUser) {
        viewModelScope.launch {
            _state.value = FeedModelState.Loading
            try {
                repository.singIn(user)
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                e.stackTrace
                _state.value = FeedModelState.Error
            }

        }

    }
}