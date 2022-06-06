package com.example.dushanbe.screens.language

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.Resource
import com.example.dushanbe.repositories.auth.AuthRepository
import com.example.dushanbe.repositories.auth.SupportModel
import com.example.dushanbe.utils.Constants
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.base.ScreenState
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.enums.InputType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val prefs:SharedPreferences) :
    BaseViewModel() {

    fun persistLanguage(lang: String) {
        prefs.edit().putBoolean(Constants.FIRST_ACCESS, false).apply()
        prefs.edit().putString(Constants.LANGUAGE, lang).apply()
    }

}