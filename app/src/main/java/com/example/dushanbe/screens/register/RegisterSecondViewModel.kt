package com.example.dushanbe.screens.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.register.Register
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.ui.Validator.validateTextFields
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
class RegisterSecondViewModel @Inject constructor() : BaseViewModel() {
    val fio = MutableLiveData("")
    val phone = MutableLiveData("")
    val extraPhone = MutableLiveData("")
    val mainAddress = MutableLiveData("")
    val dateOfBirth = MutableLiveData("")
    val number = MutableLiveData("")
    val fieldError = MutableSharedFlow<Pair<String, InputErrorType>>()
    private val numberOfFieldsRequired = MutableLiveData(6)
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
        buttonEnabled.postValue(validFields.size == numberOfFieldsRequired.value)
    }

    fun validateFio() {
        viewModelScope.launch {
            fio.asFlow().debounce(200).distinctUntilChanged().collect {
                val valid = validateTextFields("fio", it)
                addField(valid)
                fieldError.emit(valid)
            }
        }

    }

    fun validatePhone() {
        viewModelScope.launch {
            phone.asFlow().debounce(200).distinctUntilChanged().collect {
                val valid = validateTextFields("phone", it)
                addField(valid)
                fieldError.emit(valid)
            }
        }
    }

    fun validateExtraPhone() {
        viewModelScope.launch {
            extraPhone.asFlow().debounce(200).distinctUntilChanged().collect {
                val valid = if (it.equals(phone.value))
                    Pair("extraPhone", InputErrorType.REPEATED)
                else validateTextFields("extraPhone", it)
                addField(valid)
                fieldError.emit(valid)
            }
        }
    }

    fun validateNumber() {
        viewModelScope.launch {
            number.asFlow().debounce(200).distinctUntilChanged().collect {
                val valid = validateTextFields("number", it)
                addField(valid)
                fieldError.emit(valid)
            }
        }
    }

    fun validateAddress() {
        viewModelScope.launch {
            mainAddress.asFlow().debounce(200).distinctUntilChanged().collect {
                val valid = validateTextFields("address", it)
                addField(valid)
                fieldError.emit(valid)
            }
        }
    }

    fun validateBirthDay() {
        viewModelScope.launch {
            dateOfBirth.asFlow().debounce(200).distinctUntilChanged().collect {
                val valid = validateTextFields("dateOfBirth", it)
                addField(valid)
                fieldError.emit(valid)
            }
        }
    }

    private var job: Job? = null
    fun validateFields() {
        job?.cancel()
        job = viewModelScope.launch {
            validateAddress()
            validateBirthDay()
            validateExtraPhone()
            validateFio()
            validateNumber()
            validatePhone()
        }
    }

    fun initValues(register: Register) {
        phone.value=register.phone
        extraPhone.value=register.phoneEx
        fio.value=register.fio
        number.value=register.passport
        dateOfBirth.value=register.birthday
        mainAddress.value=register.address
    }

}