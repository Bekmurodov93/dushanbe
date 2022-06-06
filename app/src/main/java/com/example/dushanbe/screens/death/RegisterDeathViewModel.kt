package com.example.dushanbe.screens.death

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.helper.HelperRepository
import com.example.dushanbe.repositories.helper.HospitalType
import com.example.dushanbe.repositories.register.Form5
import com.example.dushanbe.repositories.register.RegisterRepository
import com.example.dushanbe.repositories.register.RegisterResp
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.base.ScreenState
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.ui.Validator.validateTextFields
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class RegisterDeathViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val helperRepository: HelperRepository
) : BaseViewModel() {

    val deathRegionOne = MutableLiveData(-1)
    val deathReasonOne = MutableLiveData("")
    val deathHours = MutableLiveData("")
    val deathRegionTwo = MutableLiveData(-1)
    val deathReasonTwo = MutableLiveData("")
    val maternalDeath = MutableLiveData(0)
    val childDeath = MutableLiveData(0)

    val buttonEnabled = MutableLiveData(false)
    private val numberOfValidFields = MutableLiveData(5)
    val fieldError = MutableSharedFlow<Pair<String, InputErrorType>>()
    private val listOfFields = mutableListOf<Pair<String, InputErrorType>>()

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
        buttonEnabled.postValue(validFields.size == numberOfValidFields.value)
    }

    fun validateDeathReasonOne() {
        viewModelScope.launch {
            deathReasonOne.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = validateTextFields("reason1", it)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun validateDeathRegionOne() {
        viewModelScope.launch {
            deathRegionOne.asFlow().debounce(300).distinctUntilChanged().collect {
                val validator = if (it >= 0) Pair("region1", InputErrorType.VALID)
                else Pair("region2", InputErrorType.INVALID)
                addField(validator)
                fieldError.emit(validator)
            }
        }
    }

    fun validateDeathHours() {
        viewModelScope.launch {
            deathHours.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = validateTextFields("deathHours", it)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun validateDeathReasonTwo() {
        viewModelScope.launch {
            deathReasonTwo.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = validateTextFields("reason2", it)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun validateDeathRegionTwo() {
        viewModelScope.launch {
            deathRegionTwo.asFlow().debounce(300).distinctUntilChanged().collect {
                val validator = if (it >= 0) Pair("region2", InputErrorType.VALID)
                else Pair("region2", InputErrorType.INVALID)
                addField(validator)
                fieldError.emit(validator)
            }
        }
    }

    fun validateFields() {
        validateDeathReasonOne()
        validateDeathRegionOne()
        validateDeathHours()
        validateDeathReasonTwo()
        validateDeathRegionTwo()
    }

    fun updateRequest(code: String): LiveData<RegisterResp> {
        val resp = MutableLiveData<RegisterResp>()
        viewModelScope.launch {
            val form = Form5()
            form.mlty_maternal = maternalDeath.value!!
            form.mlty_maternal_hospital_id = deathRegionOne.value!!
            form.mlty_maternal_cause = deathReasonOne.value!!
            form.mlty_child = childDeath.value!!
            form.mlty_child_recorded_days=deathHours.value!!
            form.mlty_child_cause = deathReasonTwo.value!!
            form.mlty_child_hospital_id = deathRegionTwo.value!!
            mutableScreenState.postValue(ScreenState.LOADING)
            registerRepository.updateFormFifth(this@RegisterDeathViewModel, form, code)
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    resp.postValue(it)
                }
        }
        return resp
    }

    fun getHospitals(): LiveData<List<HospitalType>> {
        val resp = MutableLiveData<List<HospitalType>>()
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)
            helperRepository.getHospitalTypes(this@RegisterDeathViewModel).collect {
                mutableScreenState.postValue(ScreenState.RENDER)
                resp.postValue(it)
            }
        }
        return resp
    }
}