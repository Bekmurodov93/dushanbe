package com.example.dushanbe.repositories.helper

import com.example.dushanbe.database.HelperDao
import com.example.dushanbe.utils.base.BaseRemoteRepository
import com.example.dushanbe.utils.base.ErrorType
import com.example.dushanbe.utils.base.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HelperRepositoryImpl @Inject constructor(
    private val helperApi: HelperApi,
    private val helperDao: HelperDao
) : HelperRepository, BaseRemoteRepository() {
    override suspend fun getHospitals(emitter: RemoteErrorEmitter): Flow<List<Hospital>> {
        return flow {
            safeApiCallNoContext(emitter) {
                 helperDao.getHospitals().collect {
                     if (it.isNotEmpty()) emit(it)
                     else {
                        val api= helperApi.getHospitals()
                         if (api.code == 401) {
                             emitter.onError(ErrorType.SESSION_EXPIRED)
                         }
                         emit(api.payload)
                         helperDao.insertHospitals(api.payload)
                     }
                 }
            }
        }
    }

    override suspend fun getHospitalTypes(emitter: RemoteErrorEmitter): Flow<List<HospitalType>> {
        return flow {
            safeApiCallNoContext(emitter) {
                helperDao.getHospitalTypes().collect {
                    if (it.isNotEmpty()) emit(it)
                    else {
                        val api= helperApi.getHospitalType()
                        if (api.code == 401) {
                            emitter.onError(ErrorType.SESSION_EXPIRED)
                        }
                        emit(api.payload)
                        helperDao.insertHospitalTypes(api.payload)
                    }
                }
            }
        }
    }
}