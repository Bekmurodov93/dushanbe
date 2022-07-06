package com.example.dushanbe.repositories.register

import com.example.dushanbe.utils.base.RemoteErrorEmitter
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun registerPregnant(emitter: RemoteErrorEmitter,register: Register):Flow<RegisterResp>
    suspend fun updateFormSecond(emitter: RemoteErrorEmitter,form2: Form2,code:String):Flow<RegisterResp>
    suspend fun updateFormThird(emitter: RemoteErrorEmitter,form2: Form3,code:String):Flow<RegisterResp>
    suspend fun updateFormFourth(emitter: RemoteErrorEmitter,form2: Form4,code:String):Flow<RegisterResp>
    suspend fun updateFormFifth(emitter: RemoteErrorEmitter,form2: Form5,code:String):Flow<RegisterResp>
    suspend fun updateFormFirst(emitter: RemoteErrorEmitter,register: Register,code:String):Flow<RegisterResp>
    suspend fun getLocalPatients(emitter: RemoteErrorEmitter):Flow<List<Register>>
    suspend fun syncLocalPatients(emitter: RemoteErrorEmitter):Flow<Boolean>
}