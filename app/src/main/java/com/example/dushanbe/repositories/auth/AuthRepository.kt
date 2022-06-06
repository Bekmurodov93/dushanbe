package com.example.dushanbe.repositories.auth

import com.example.dushanbe.repositories.Resource
import com.example.dushanbe.utils.base.RemoteErrorEmitter
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.enums.InputType
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(emitter: RemoteErrorEmitter, login :String,password:String): Flow<Resource<String>>
    suspend fun forgot(emitter: RemoteErrorEmitter): Flow<SupportModel>
    fun getFields(): MutableList<Pair<InputType, InputErrorType>>
    fun getProfile():Flow<User?>
    fun logout()
}