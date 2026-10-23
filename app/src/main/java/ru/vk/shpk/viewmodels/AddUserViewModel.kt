package ru.vk.shpk.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vk.shpk.dto.NewUser
import ru.vk.shpk.feedmodel.FeedModelState
import ru.vk.shpk.repository.AddUserRepositoryImpl

class AddUserViewModel(context: Application) : AndroidViewModel(context) {
    private val repository = AddUserRepositoryImpl()

    private val _state = MutableLiveData<FeedModelState>()
    val state: MutableLiveData<FeedModelState>
        get() = _state

    fun serverResponse(): LiveData<String> {
        return repository.serverResponse
    }

    fun addUserOnServer(user: NewUser){
        viewModelScope.launch {
            _state.value = FeedModelState.Loading
            try {
                repository.addUserOnServer(user)
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                e.stackTrace
                _state.value = FeedModelState.Error
            }
        }
    }
}