package com.example.dushanbe.repositories.helper

import retrofit2.http.GET

interface HelperApi {
    @GET("/api/getHospitals")
    suspend fun getHospitals():HelperHospitalResp

    @GET("/api/getHospitalTypes")
    suspend fun getHospitalType():HelperHospitalTypeResp
}