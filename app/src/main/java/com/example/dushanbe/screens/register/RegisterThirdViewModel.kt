package com.example.dushanbe.screens.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.register.*
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.base.ScreenState
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.ui.Validator
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
class RegisterThirdViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : BaseViewModel() {
    val lastMenstruationDate = MutableLiveData("")
    val estimatedBirthDate = MutableLiveData("")
    val parity = MutableLiveData("")


    val fieldError = MutableSharedFlow<Pair<String, InputErrorType>>()
    private val numberOfFieldsRequired = MutableLiveData(2)
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

    fun validateMenstruation() {
        viewModelScope.launch {
            lastMenstruationDate.asFlow().debounce(200).distinctUntilChanged().collect {
                val valid = Validator.validateTextFields("dateOfMenstruation", it)
                addField(valid)
                fieldError.emit(valid)
            }
        }
    }

    fun validateParity() {
        viewModelScope.launch {
            parity.asFlow().debounce(200).distinctUntilChanged().collect {
                val valid = Validator.validateDigitField("parity", it)
                addField(valid)
                fieldError.emit(valid)
            }
        }
    }

    private var job: Job? = null
    fun validateFields() {
        job?.cancel()
        job = viewModelScope.launch {
            validateParity()
            validateMenstruation()
        }
        job?.start()
    }

    fun register(register: Register): LiveData<RegisterResp> {
        val resp = MutableLiveData<RegisterResp>()
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)
            registerRepository.registerPregnant(this@RegisterThirdViewModel, register)
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    resp.postValue(it)
                }
        }
        return resp
    }

    fun initValues(register: Register) {
        lastMenstruationDate.value = register.infoMenstruation
        estimatedBirthDate.value = register.infoEstimatedDate
        parity.value = register.infoParity.toString()
    }

    fun update(register: Register, code: String): LiveData<RegisterModel?> {
        val resp = MutableLiveData<RegisterModel?>()
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)
            registerRepository.updateFormFirst(this@RegisterThirdViewModel, register, code)
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    if (it.code == 200 || it.code == 201)
                        resp.postValue(it.payload)
                    else resp.postValue(null)
                }
        }
        return resp
    }
}