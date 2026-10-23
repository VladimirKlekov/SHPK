package ru.vk.shpk.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vk.shpk.dto.UserBd
import ru.vk.shpk.feedmodel.FeedModelState
import ru.vk.shpk.repository.ListUsersRepositoryImpl

class ListUsersViewModel(
    context: Application
) : AndroidViewModel(context) {
    private val repository = ListUsersRepositoryImpl()

    private val _state = MutableLiveData<FeedModelState>()
    val state: MutableLiveData<FeedModelState>
        get() = _state

    val listUsersBd: LiveData<List<UserBd>>
        get() = repository.listUsersBd

    init {
        getListUsersOnServer()
    }

    fun getListUsersOnServer() {
        viewModelScope.launch {
            _state.value = FeedModelState.Loading
            try {
                repository.getListUsersOnServer()
                println(repository.getListUsersOnServer())
                _state.value = FeedModelState.Idle
            } catch (e: Exception) {
                _state.value = FeedModelState.Error
            }
        }
    }


    fun deleteUserOnServer(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteUserOnServer(id)
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    fun refreshListUsers() {
        viewModelScope.launch {
            try {
                repository.getListUsersOnServer()
                _state.value = FeedModelState.Refresh
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}