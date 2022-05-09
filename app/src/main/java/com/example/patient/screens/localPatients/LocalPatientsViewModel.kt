package com.example.patient.screens.localPatients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.patient.networking.interceptors.NetworkMonitor
import com.example.patient.repositories.register.Register
import com.example.patient.repositories.register.RegisterRepository
import com.example.patient.utils.base.BaseViewModel
import com.example.patient.utils.base.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalPatientsViewModel @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val networkMonitor: NetworkMonitor
): BaseViewModel() {
    val netWorkConnect=MutableLiveData<Boolean?>(null)
    init {
        netWorkConnect.postValue(networkMonitor.isConnected())
    }

    fun getLocalPatients(): LiveData<List<Register>?> {
        val resp = MutableLiveData<List<Register>?>()
        viewModelScope.launch {
            mutableScreenState.postValue(ScreenState.LOADING)
            registerRepository.getLocalPatients(this@LocalPatientsViewModel).collect {
                mutableScreenState.postValue(ScreenState.RENDER)
                resp.postValue(it)
            }
        }
        return  resp
    }

    fun syncLocalPatients() {
       if (networkMonitor.isConnected()){
           viewModelScope.launch {
               mutableScreenState.postValue(ScreenState.LOADING)
               registerRepository.syncLocalPatients(this@LocalPatientsViewModel).collect {
                   mutableScreenState.postValue(ScreenState.RENDER)
               }
           }
       }else {
           netWorkConnect.postValue(false)
       }

    }
}