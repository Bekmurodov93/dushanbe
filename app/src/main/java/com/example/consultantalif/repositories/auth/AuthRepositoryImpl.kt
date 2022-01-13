package com.example.consultantalif.repositories.auth

import android.content.SharedPreferences
import android.util.Log
import com.example.consultantalif.repositories.Resource
import com.example.consultantalif.utils.Constants
import com.example.consultantalif.utils.base.BaseRemoteRepository
import com.example.consultantalif.utils.base.RemoteErrorEmitter
import com.example.consultantalif.utils.enums.InputErrorType
import com.example.consultantalif.utils.enums.InputType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs: SharedPreferences
) : AuthRepository,
    BaseRemoteRepository() {
    override suspend fun login(
        emitter: RemoteErrorEmitter,
        jsonString: String
    ): Flow<Resource<String>> {
        return flow {
            safeApiCallNoContext(emitter) {
                emit(Resource.Loading())
                val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
                val resp = api.login(body = requestBody).body()
                if (resp?.code == 200) {
                    emit(Resource.Success(resp.data ?: ""))
                    prefs.edit().putString(Constants.AUTH_TOKEN, resp.data).apply()
                } else {
                    emit(Resource.Error(resp?.message ?: "", resp?.code ?: -1))
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