package com.example.patient.screens

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.patient.repositories.register.Register
import com.example.patient.utils.Constants
import com.example.patient.utils.Constants.AUTH_TOKEN
import com.example.patient.utils.Constants.LANGUAGE
import com.example.patient.utils.Constants.LANGUAGE_CHANGED
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val prefs: SharedPreferences) : ViewModel() {
    val authToken = MutableLiveData("default")

    init {
        authToken.postValue(prefs.getString(AUTH_TOKEN, ""))
    }

    val register = Register()

    val lang = prefs.getString(LANGUAGE, "ru")
    fun persistLanguage(lang: String) {
        viewModelScope.launch(Dispatchers.Main) {
            prefs.edit().putString(LANGUAGE, lang).apply()
        }
    }
}