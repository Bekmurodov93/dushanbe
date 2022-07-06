package com.example.dushanbe.repositories.register

import com.example.dushanbe.database.PatientDao
import com.example.dushanbe.networking.interceptors.NetworkMonitor
import com.example.dushanbe.utils.base.BaseRemoteRepository
import com.example.dushanbe.utils.base.ErrorType
import com.example.dushanbe.utils.base.RemoteErrorEmitter
import com.example.dushanbe.utils.ui.normalize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject


class RegisterRepositoryImpl @Inject constructor(
    private val api: RegisterApi,
    private val patientDao: PatientDao, private val networkMonitor: NetworkMonitor
) :
    RegisterRepository, BaseRemoteRepository() {
    override suspend fun registerPregnant(
        emitter: RemoteErrorEmitter,
        register: Register
    ): Flow<RegisterResp> {
        return flow {
            if (!networkMonitor.isConnected()){
                register.id= UUID.randomUUID().toString()
                patientDao.insertPatient(register)
                emit(
                    RegisterResp(
                        code = 100,
                        message = register.id,
                        payload = null
                    )
                )
            } else {
                    try {
                        val resp = api.register(
                            register.fio,
                            register.publishDate.normalize(),
                            register.type,
                            register.birthday.normalize(),
                            register.passport,
                            register.address,
                            register.phone,
                            register.phoneEx,
                            register.infoMenstruation.normalize(),
                            register.infoEstimatedDate.normalize(),
                            register.infoParity,
                            register.infoBirthPermit
                        )
                        if (resp.code == 401) {
                            emitter.onError(ErrorType.SESSION_EXPIRED)
                        }
                        emit(resp)
                    }catch (e:Exception){
                        patientDao.insertPatient(register)
                        emit(
                            RegisterResp(
                                code = 100,
                                message = register.id,
                                payload = null
                            )
                        )
                    }
            }
        }
    }

    override suspend fun updateFormSecond(
        emitter: RemoteErrorEmitter,
        form2: Form2,
        code: String
    ): Flow<RegisterResp> {
        return flow {
            safeApiCallNoContext(emitter) {
                form2.visit_date_1 = form2.visit_date_1.normalize()
                form2.visit_date_2 = form2.visit_date_2.normalize()
                val resp = api.updateFormSecond(
                    code,
                    form2.ch_visit_date_1,
                    form2.visit_date_1,
                    form2.ch_visit_date_2,
                    form2.visit_date_2
                )
                if (resp.code == 401) {
                    emitter.onError(ErrorType.SESSION_EXPIRED)
                }
                emit(resp)
            }
        }
    }

    override suspend fun updateFormThird(
        emitter: RemoteErrorEmitter,
        form2: Form3,
        code: String
    ): Flow<RegisterResp> {
        return flow {
            safeApiCallNoContext(emitter) {
                val resp = api.updateFormThird(
                    code = code,
                    egcy_init_hospital_type = form2.egcy_init_hospital_type,
                    egcy_init_date = form2.egcy_init_date.normalize(),
                    egcy_init_diagnosis = form2.egcy_init_diagnosis,
                    egcy_init_treatment = form2.egcy_init_treatment,
                    egcy_init_phonogram = form2.egcy_init_phonogram,
                    egcy_init_relatives = form2.egcy_init_relatives,
                    egcy_init_projects = form2.egcy_init_projects,
                    egcy_init_accompanying = form2.egcy_init_accompanying,
                    egcy_init_transport_provided = form2.egcy_init_transport_provided,
                    egcy_init_accompanying_gender = form2.egcy_init_accompanying_gender
                )
                if (resp.code == 401) {
                    emitter.onError(ErrorType.SESSION_EXPIRED)
                }
                emit(resp)
            }
        }
    }

    override suspend fun updateFormFourth(
        emitter: RemoteErrorEmitter,
        form2: Form4,
        code: String
    ): Flow<RegisterResp> {
        return flow {
            safeApiCallNoContext(emitter) {
                val resp = api.updateFormFourth(
                    code = code,
                    rtn_accept_referral = form2.rtn_accept_referral,
                    ch_rtn_accept_newborn_1 = form2.ch_rtn_accept_newborn_1,
                    rtn_accept_newborn_1 = form2.rtn_accept_newborn_1.normalize()
                )
                if (resp.code == 401) {
                    emitter.onError(ErrorType.SESSION_EXPIRED)
                }
                emit(resp)
            }
        }
    }

    override suspend fun updateFormFifth(
        emitter: RemoteErrorEmitter,
        form2: Form5,
        code: String
    ): Flow<RegisterResp> {
        return flow {
            safeApiCallNoContext(emitter) {
                val resp = api.updateFormFifth(
                    code = code,
                    mlty_maternal = form2.mlty_maternal,
                    mlty_maternal_hospital_id = form2.mlty_maternal_hospital_id,
                    mlty_child_cause = form2.mlty_child_cause,
                    mlty_child = form2.mlty_child,
                    mlty_maternal_cause = form2.mlty_maternal_cause,
                    mlty_child_recorded_days = form2.mlty_child_recorded_days,
                    mlty_child_hospital_id = form2.mlty_child_hospital_id
                )
                if (resp.code == 401) {
                    emitter.onError(ErrorType.SESSION_EXPIRED)
                }
                emit(resp)

            }
        }
    }

    override suspend fun updateFormFirst(
        emitter: RemoteErrorEmitter,
        register: Register, code: String
    ): Flow<RegisterResp> {
        return flow {
            safeApiCallNoContext(emitter) {
                val resp = api.updateFormFirst(
                    code = code,
                    fio = register.fio,
                    publishDate = register.publishDate,
                    type = register.type,
                    passport = register.passport,
                    birthday = register.birthday.normalize(),
                    address = register.address,
                    phone = register.phone,
                    phoneEx = register.phoneEx,
                    infoMenstruation = register.infoMenstruation,
                    infoEstimatedDate = register.infoEstimatedDate.normalize(),
                    infoBirthPermit = register.infoBirthPermit,
                    infoParity = register.infoParity
                )
                if (resp.code == 401) {
                    emitter.onError(ErrorType.SESSION_EXPIRED)
                }
                emit(resp)
            }
        }
    }

    override suspend fun getLocalPatients(emitter: RemoteErrorEmitter): Flow<List<Register>> {
        return patientDao.getPatients()
    }

    override suspend fun syncLocalPatients(emitter: RemoteErrorEmitter): Flow<Boolean> {
        return flow {
            patientDao.getPatients().collect { list ->
                for (patient in list) {
                    registerPregnant(emitter, patient).collect {
                        if (it.code == 200 || it.code == 201)
                            patientDao.deletePatient(patient)
                        if (it.code == 401) {
                            emitter.onError(ErrorType.SESSION_EXPIRED)
                        }
                    }
                }
                emit(true)
            }
            emit(false)
        }
    }

}