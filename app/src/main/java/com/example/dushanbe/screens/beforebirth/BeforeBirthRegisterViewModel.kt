package com.example.dushanbe.screens.beforebirth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.register.Form2
import com.example.dushanbe.repositories.register.RegisterRepository
import com.example.dushanbe.repositories.register.RegisterResp
import com.example.dushanbe.utils.Constants
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.base.ScreenState
import com.example.dushanbe.utils.enums.InputErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@FlowPreview
@HiltViewModel
class BeforeBirthRegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : BaseViewModel() {
    val date = MutableLiveData("")
    val secondDate = MutableLiveData("")
    val firstAnalysis = MutableLiveData(false)
    val secondAnalysis = MutableLiveData(false)
    val buttonEnabled = MutableLiveData(false)
    private val numberOfValidFields = MutableLiveData(0)
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


    fun addField() {
        val current = numberOfValidFields.value!! + 1
        numberOfValidFields.postValue(current)
    }

    fun removeField() {
        val current = numberOfValidFields.value!! - 1
        numberOfValidFields.postValue(current)
    }

    fun validateDate() {
        viewModelScope.launch {
            date.asFlow().debounce(400).distinctUntilChanged().collect {
                val err = if (it.matches(Constants.dateRegex.toRegex())) Pair(
                    "date",
                    InputErrorType.VALID
                )
                else Pair("date", InputErrorType.INVALID)
                addField(err)
                fieldError.emit(err)

            }
        }
    }

    fun validateSecondDate() {
        viewModelScope.launch {
            date.asFlow().debounce(400).distinctUntilChanged().collect {
                val err = if (it.matches(Constants.dateRegex.toRegex())) Pair(
                    "2date",
                    InputErrorType.VALID
                )
                else Pair("2date", InputErrorType.INVALID)
                addField(err)
                fieldError.emit(err)
            }
        }
    }

    fun updateRequest(code: String): LiveData<RegisterResp> {
        val resp = MutableLiveData<RegisterResp>()
        viewModelScope.launch {
            val form = Form2()
            form.ch_visit_date_1 = if (firstAnalysis.value!!) 1 else 0
            form.visit_date_1 = date.value!!
            form.ch_visit_date_2 = if (secondAnalysis.value!!) 1 else 0
            form.visit_date_2 = secondDate.value!!
            mutableScreenState.postValue(ScreenState.LOADING)
            registerRepository.updateFormSecond(this@BeforeBirthRegisterViewModel, form, code)
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    resp.postValue(it)
                }
        }
        return  resp
    }
}