package com.example.dushanbe.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.dushanbe.repositories.search.SearchRepository
import com.example.dushanbe.repositories.search.SearchResp
import com.example.dushanbe.utils.Constants
import com.example.dushanbe.utils.base.BaseViewModel
import com.example.dushanbe.utils.base.ScreenState
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.ui.normalize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    val searchInput = MutableLiveData("")
    val fio = MutableLiveData("")
    val identNumber = MutableLiveData("")
    val phone = MutableLiveData("")
    val passport = MutableLiveData("")
    val birthday = MutableLiveData("")
    val fieldError = MutableSharedFlow<Pair<String, InputErrorType>>()

    private val listOfFields = mutableListOf<Pair<String, InputErrorType>>()
//
    fun validateDate() {
        viewModelScope.launch {
            birthday.asFlow().debounce(300).distinctUntilChanged().collect {
                val err = if (it.matches(Constants.dateRegex.toRegex())) Pair("date", InputErrorType.VALID)
                else Pair("date", InputErrorType.INVALID)
//                addField(err)
                fieldError.emit(err)
            }
        }
    }
    fun getByIdPhone():LiveData<SearchResp> {
        val resp=MutableLiveData<SearchResp>()
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)
            val value = searchInput.value ?: ""
            searchRepository.getPatientsByIDPhone(this@SearchViewModel, value).collect {
                mutableScreenState.postValue(ScreenState.RENDER)
                resp.postValue(it)
            }
        }
        return  resp
    }

    fun getPatients(offset: Int, limit: Int):LiveData<SearchResp> {
        val resp=MutableLiveData<SearchResp>()
        viewModelScope.launch {
            val json = JSONObject()
            json.put("fio", fio.value ?: "")
            json.put("passport", passport.value ?: "")
            json.put("code", identNumber.value ?: "")
            json.put("birthdate", (birthday.value ?: "").normalize())
            json.put("phone", phone.value ?: "")
            searchRepository.getPatients(this@SearchViewModel, offset, limit, json.toString())
                .collect {
                    resp.postValue(it)
                }
        }
        return  resp
    }
    fun getPatientsInit(offset: Int, limit: Int):LiveData<SearchResp> {
        val resp=MutableLiveData<SearchResp>()
        viewModelScope.launch {
            val json = JSONObject()
            json.put("fio", fio.value ?: "")
            json.put("passport", passport.value ?: "")
            json.put("code", identNumber.value ?: "")
            json.put("birthdate", birthday.value ?: "")
            json.put("phone", phone.value ?: "")
            mutableScreenState.postValue(ScreenState.LOADING)
            searchRepository.getPatients(this@SearchViewModel, offset, limit, json.toString())
                .collect {
                    mutableScreenState.postValue(ScreenState.RENDER)
                    resp.postValue(it)
                }
        }
        return  resp
    }
}