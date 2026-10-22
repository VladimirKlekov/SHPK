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
            try {
                repository.getListUsersOnServer()
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }

    fun serverResponse(): LiveData<String> {
        return repository.serverResponse
    }

    fun getListUsersOnServer(): LiveData<List<UserBd>> {
        return repository.listUsersBd
    }

    fun deleteUserOnServer(id:Int){
        viewModelScope.launch {
            try {
                repository.deleteUserOnServer(id)
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
    fun refreshListUsers(){
        viewModelScope.launch {
            try {
                repository.getListUsersOnServer()
            } catch (e: Exception) {
                e.stackTrace
            }
        }
    }
}