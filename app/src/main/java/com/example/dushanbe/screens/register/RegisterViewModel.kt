package com.example.dushanbe.screens.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.auth.AuthRepository
import com.example.dushanbe.repositories.auth.User
import com.example.dushanbe.repositories.helper.HelperRepository
import com.example.dushanbe.repositories.helper.HospitalType
import com.example.dushanbe.repositories.register.Register
import com.example.dushanbe.utils.Constants.dateRegex
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.base.ScreenState
import com.example.dushanbe.utils.enums.InputErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val helperRepository: HelperRepository
) : BaseViewModel() {
    val date = MutableLiveData("")
    val type = MutableLiveData(-1)
    val user = MutableLiveData<User?>(null)
    var requiredFiledNumber = 2
    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            authRepository.getProfile().collect {
                user.postValue(it)
            }
        }

    }

    val fieldError = MutableSharedFlow<Pair<String, InputErrorType>>()

    private val listOfFields = mutableListOf<Pair<String, InputErrorType>>()

    val buttonEnabled = MutableLiveData(false)

    private fun addField(field: Pair<String, InputErrorType>) {
        exists(field)
        listOfFields.add(field)
        enableButton()
    }

    private fun exists(field: Pair<String, InputErrorType>) {
        listOfFields.remove(field)
    }

    private fun enableButton() {
        val validFields = listOfFields.filter { pair -> pair.second == InputErrorType.VALID }
        buttonEnabled.postValue(validFields.size == requiredFiledNumber)
    }

    fun validateDate() {
        viewModelScope.launch {
            date.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = if (it.matches(dateRegex.toRegex())) Pair("date", InputErrorType.VALID)
                else Pair("date", InputErrorType.INVALID)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun validateType() {
        viewModelScope.launch {
            type.asFlow().debounce(300).distinctUntilChanged().collect {
                val validator = if (it >= 0) Pair("type", InputErrorType.VALID)
                else Pair("type", InputErrorType.INVALID)
                addField(validator)
                fieldError.emit(validator)
            }
        }
    }
    private var job: Job?=null
    fun validateFields(){
        job?.cancel()
        job=viewModelScope.launch {
            validateType()
            validateDate()
        }
    }

    fun getHospitals(): LiveData<List<HospitalType>> {
        val resp = MutableLiveData<List<HospitalType>>()
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)
            helperRepository.getHospitalTypes(this@RegisterViewModel).collect {
                mutableScreenState.postValue(ScreenState.RENDER)
                resp.postValue(it)
            }
        }
        return resp
    }

    fun initValues(register: Register) {
        this.date.value=register.publishDate
    }
}