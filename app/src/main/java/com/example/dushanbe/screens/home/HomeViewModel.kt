package com.example.dushanbe.screens.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.auth.AuthRepository
import com.example.dushanbe.repositories.auth.User
import com.example.dushanbe.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : BaseViewModel() {
    val phone = MutableLiveData("")
    val user = MutableLiveData<User?>(null)
    fun getProfile() {
        viewModelScope.launch {
            authRepo.getProfile().collect {
                user.postValue(it)
            }
        }
    }
}