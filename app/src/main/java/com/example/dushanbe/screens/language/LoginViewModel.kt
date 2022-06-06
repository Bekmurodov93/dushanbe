package com.example.dushanbe.screens.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.Resource
import com.example.dushanbe.repositories.auth.AuthRepository
import com.example.dushanbe.repositories.auth.SupportModel
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
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val isLogin = MutableLiveData(false)
    val fieldError = MutableLiveData(Pair(InputType.NONE, InputErrorType.EMPTY))
    private val setOfFields by lazy { authRepository.getFields() }

    fun login(): LiveData<Resource<String>> {
        val resp = MutableLiveData<Resource<String>>()
        viewModelScope.launch(Dispatchers.IO) {
            mutableScreenState.postValue(ScreenState.LOADING)
            authRepository.login(this@LoginViewModel, email.value!!, password.value!!)
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    resp.postValue(it)
                }
        }
        return resp
    }
    fun forgot(): LiveData<SupportModel> {
        val resp = MutableLiveData<SupportModel>()
        viewModelScope.launch(Dispatchers.IO) {
            mutableScreenState.postValue(ScreenState.LOADING)
            authRepository.forgot(this@LoginViewModel)
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    resp.postValue(it)
                }
        }
        return resp
    }
    fun validateLoginFields(inputType: InputType) {
        val str: String
        val pair: Pair<InputType, InputErrorType>
        if (inputType == InputType.EMAIL) {
            str = email.value ?: ""
            pair =
                when {
                    str.isEmpty() -> {
                        Pair(inputType, InputErrorType.EMPTY)
                    }
                    str.length > 4 -> {
                        Pair(inputType, InputErrorType.VALID)
                    }
                    else -> {
                        Pair(inputType, InputErrorType.MISMATCH)
                    }
                }

        } else {
            str = password.value ?: ""
            pair = when {
                str.isEmpty() -> {
                    Pair(inputType, InputErrorType.EMPTY)
                }
                str.length > 4 -> {
                    Pair(inputType, InputErrorType.VALID)
                }
                else -> {
                    Pair(inputType, InputErrorType.INVALID)
                }
            }
        }
        alterField(pair)
        fieldError.postValue(pair)
        enableButton()
    }

    //    to be continued
    private fun enableButton() {
        val validFields = setOfFields.filter { it.second == InputErrorType.VALID }
        isLogin.postValue(validFields.size == setOfFields.size)
    }

    private fun alterField(pair: Pair<InputType, InputErrorType>) {
        val field = setOfFields.find { pair.first == it.first }
        val index = setOfFields.indexOf(field)
        if (index > -1) setOfFields.removeAt(index)
        setOfFields.add(pair)
    }
    fun logout(){
        authRepository.logout()
    }
}