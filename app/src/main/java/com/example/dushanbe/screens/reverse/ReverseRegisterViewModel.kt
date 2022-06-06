package com.example.dushanbe.screens.reverse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.register.*
import com.example.dushanbe.utils.Constants
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.base.ErrorType
import com.example.dushanbe.utils.base.ScreenState
import com.example.dushanbe.utils.enums.InputErrorType
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
class ReverseRegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : BaseViewModel() {
    val date = MutableLiveData("")

    val firstReverse = MutableLiveData(false)

    val firstDiagnoseDate = MutableLiveData(false)

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
        buttonEnabled.postValue(validFields.size == 1)
    }

    fun validateDate() {
        viewModelScope.launch {
            date.asFlow().debounce(100).distinctUntilChanged().collect {
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

    fun updateRequest(code: String): LiveData<RegisterResp> {
        val resp = MutableLiveData<RegisterResp>()
        viewModelScope.launch {
            val form = Form4()
            form.rtn_accept_newborn_1 = date.value!!
            form.rtn_accept_referral = if (firstDiagnoseDate.value!!) 1 else 0
            form.ch_rtn_accept_newborn_1 = if (firstReverse.value!!) 1 else 0
            mutableScreenState.postValue(ScreenState.LOADING)
            registerRepository.updateFormFourth(this@ReverseRegisterViewModel, form, code)
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    resp.postValue(it)
                    if (it.code == 401) {
                        mutableErrorType.postValue(ErrorType.SESSION_EXPIRED)
                    }
                }
        }
        return resp
    }
}