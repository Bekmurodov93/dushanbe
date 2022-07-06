package com.example.dushanbe.repositories.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.dushanbe.repositories.register.RegisterModel
import com.example.dushanbe.utils.base.BaseRemoteRepository
import com.example.dushanbe.utils.base.ErrorType
import com.example.dushanbe.utils.base.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApi: SearchApi
) : BaseRemoteRepository(), SearchRepository {

    override suspend fun getPatientsByIDPhone(
        emitter: RemoteErrorEmitter,
        value: String
    ): Flow<SearchResp> {
        return flow {
            safeApiCallNoContext(emitter) {
                val resp=searchApi.getPatientsByFioIDPhone(value)
                if (resp.code==401)
                    emitter.onError(ErrorType.SESSION_EXPIRED)
                emit(resp)
            }
        }
    }

    override suspend fun getPatientsByIDPhonePager(
        emitter: RemoteErrorEmitter,
        value: String
    ): Flow<PagingData<RegisterModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPatientsPager(
        emitter: RemoteErrorEmitter,
        offset: Int,
        limit: Int,
        jsonBody: String
    ): Flow<PagingData<RegisterModel>> {
       return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PatientsPagingSource(searchApi,offset,limit,jsonBody) }
        ).flow
    }

    override suspend fun getPatients(
        emitter: RemoteErrorEmitter,
        offset: Int,
        limit: Int,
        jsonBody: String
    ): Flow<SearchResp> {
        return flow {
            safeApiCallNoContext(emitter) {
                val body = jsonBody.toRequestBody()
                val resp = searchApi.getPatients(offset, limit, body)
                if (resp.code==401)
                    emitter.onError(ErrorType.SESSION_EXPIRED)
                emit(resp)
            }
        }
    }
}