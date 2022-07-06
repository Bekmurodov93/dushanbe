package com.example.dushanbe.screens

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.register.Register
import com.example.dushanbe.utils.Constants
import com.example.dushanbe.utils.Constants.AUTH_TOKEN
import com.example.dushanbe.utils.Constants.LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val prefs: SharedPreferences) : ViewModel() {
    val authToken = MutableLiveData("default")
    val firstAccess=MutableLiveData<Boolean?>(null)
    init {
        firstAccess.postValue(prefs.getBoolean(Constants.FIRST_ACCESS,true))
        authToken.postValue(prefs.getString(AUTH_TOKEN, ""))
    }

    val register = Register()

//    val registerModel=RegisterModel()

    val lang = prefs.getString(LANGUAGE, "ru")
    fun persistLanguage(lang: String) {
        viewModelScope.launch(Dispatchers.Main) {
            prefs.edit().putString(LANGUAGE, lang).apply()
        }
    }
}