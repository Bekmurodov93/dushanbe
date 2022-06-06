package com.example.dushanbe.screens.emergency

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.helper.HelperRepository
import com.example.dushanbe.repositories.helper.Hospital
import com.example.dushanbe.repositories.register.Form3
import com.example.dushanbe.repositories.register.RegisterRepository
import com.example.dushanbe.repositories.register.RegisterResp
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.base.ScreenState
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.ui.Validator.validateTextFields
import com.example.dushanbe.utils.ui.normalize
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
class EmergencyViewModel @Inject constructor(
    private val helperRepository: HelperRepository,
    private val registerRepository: RegisterRepository
) : BaseViewModel() {
    val type = MutableLiveData(-1)
    val date = MutableLiveData("")
    val diagnose = MutableLiveData("")
    val process = MutableLiveData("")
    val nurse = MutableLiveData("")
    val beenInformed = MutableLiveData(false)
    val hasBeenDirected = MutableLiveData(false)
    val hasHelperNurse = MutableLiveData(false)
    val transportSupported = MutableLiveData(false)
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

    fun validateDate() {
        viewModelScope.launch {
            date.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = validateTextFields("date", it)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun validateDiagnose() {
        viewModelScope.launch {
            diagnose.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = validateTextFields("diagnose", it)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun validateProcess() {
        viewModelScope.launch {
            process.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = validateTextFields("process", it)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun validateNurse() {
        viewModelScope.launch {
            nurse.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = validateTextFields("nurse", it)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun validateAll() {
        validateDate()
        validateType()
        validateDiagnose()
        validateProcess()
        validateNurse()
    }
    fun getHospitals(): LiveData<List<Hospital>> {
        val resp = MutableLiveData<List<Hospital>>()
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)
            helperRepository.getHospitals(this@EmergencyViewModel).collect {
                mutableScreenState.postValue(ScreenState.RENDER)
                resp.postValue(it)
            }
        }
        return resp
    }
    fun updateRequest(code: String):  LiveData<RegisterResp>  {
        val resp = MutableLiveData<RegisterResp>()
        viewModelScope.launch {
            val form = Form3()
            form.egcy_init_date=date.value!!.normalize()
            form.egcy_init_treatment=process.value!!
            form.egcy_init_diagnosis=diagnose.value!!
            form.egcy_init_accompanying_gender=nurse.value!!
            form.egcy_init_hospital_type=type.value!!
            form.egcy_init_accompanying=if (hasHelperNurse.value!!) 1 else 0
            form.egcy_init_relatives=if (beenInformed.value!!) 1 else 0
            form.egcy_init_transport_provided=if (transportSupported.value!!) 1 else 0
            form.egcy_init_projects=if (hasBeenDirected.value!!) 1 else 0
            mutableScreenState.postValue(ScreenState.LOADING)
            registerRepository.updateFormThird(this@EmergencyViewModel, form, code)
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    if (it.code == 200 || it.code == 201)
                        resp.postValue(it)
                }
        }
        return  resp
    }
}