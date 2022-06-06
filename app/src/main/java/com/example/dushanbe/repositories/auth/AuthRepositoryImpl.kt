package com.example.dushanbe.repositories.auth

import android.content.SharedPreferences
import android.util.Log
import com.example.dushanbe.database.ProfileDao
import com.example.dushanbe.repositories.Resource
import com.example.dushanbe.utils.Constants
import com.example.dushanbe.utils.base.BaseRemoteRepository
import com.example.dushanbe.utils.base.RemoteErrorEmitter
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.enums.InputType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val prefs: SharedPreferences,
    private val profileDao: ProfileDao
) : AuthRepository,
    BaseRemoteRepository() {
    override suspend fun login(
        emitter: RemoteErrorEmitter,
        login: String, password: String
    ): Flow<Resource<String>> {
        return flow {
            safeApiCallNoContext(emitter) {
                val resp = api.login(login, password)
                if (resp.code == 200) {
                    emit(Resource.Success(resp.message))
                    prefs.edit().putString(Constants.AUTH_TOKEN, resp.token).apply()
                    Log.v("tag", "$resp")
                    resp.user?.let { profileDao.insertPromoter(it) }
                } else {
                    emit(Resource.Error(resp.message))
                }
            }
        }
    }

    override suspend fun forgot(emitter: RemoteErrorEmitter): Flow<SupportModel> {
        return flow {
            safeApiCallNoContext(emitter){
                emit( api.forgot())
            }
        }
    }

    override fun getFields(): MutableList<Pair<InputType, InputErrorType>> =
        mutableListOf(
            Pair(InputType.EMAIL, InputErrorType.EMPTY),
            Pair(InputType.PASSWORD, InputErrorType.EMPTY)
        )

    override fun getProfile(): Flow<User?> = profileDao.getProfile()

    override fun logout() {
        prefs.edit().putString(Constants.AUTH_TOKEN, "").apply()
    }

}