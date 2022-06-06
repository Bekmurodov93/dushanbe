package com.example.dushanbe.screens

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dushanbe.utils.Constants.FIRST_ACCESS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(prefs: SharedPreferences):ViewModel() {
    val firstAccess=MutableLiveData<Boolean?>(null)
    init {
        firstAccess.postValue(prefs.getBoolean(FIRST_ACCESS,true))
    }
}