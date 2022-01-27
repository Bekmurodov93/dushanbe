package com.example.patient.repositories.auth

import android.content.SharedPreferences
import com.example.patient.database.TestDao
import com.example.patient.repositories.Resource
import com.example.patient.utils.Constants
import com.example.patient.utils.base.BaseRemoteRepository
import com.example.patient.utils.base.RemoteErrorEmitter
import com.example.patient.utils.enums.InputErrorType
import com.example.patient.utils.enums.InputType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs: SharedPreferences,
    private val testDao: TestDao
) : AuthRepository,
    BaseRemoteRepository() {
    override suspend fun login(
        emitter: RemoteErrorEmitter,
        jsonString: String
    ): Flow<Resource<String>> {
        return flow {
            safeApiCallNoContext(emitter) {
                val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
                val resp = api.login(body = requestBody)
                if (resp.code == 200) {
                    emit(Resource.Success(resp.data?.token?: ""))
                    prefs.edit().putString(Constants.AUTH_TOKEN, resp.data?.token).apply()
                } else {
                    emit(Resource.Error(resp.message, resp.code))
                }
            }
        }
    }

    override fun getFields(): MutableList<Pair<InputType, InputErrorType>> =
        mutableListOf(
            Pair(InputType.EMAIL, InputErrorType.EMPTY),
            Pair(InputType.PASSWORD, InputErrorType.EMPTY)
        )
}