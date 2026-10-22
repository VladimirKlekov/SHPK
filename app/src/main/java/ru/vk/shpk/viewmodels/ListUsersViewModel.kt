package ru.vk.shpk.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vk.shpk.dto.UserBd
import ru.vk.shpk.repository.ListUsersRepositoryImpl

class ListUsersViewModel(
    context: Application
) : AndroidViewModel(context) {
    private val repository = ListUsersRepositoryImpl()

    init {
        viewModelScope.launch {
            repository.getListUsersOnServer()
        }
    }

    fun getListUsersOnServer(): LiveData<List<UserBd>> {
        return repository.listUsersBd
    }
}