package com.example.dushanbe.repositories.search

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SearchApi {

    @GET("/api/searchPregnantByFioIdPhone/{value}")
    suspend fun getPatientsByFioIDPhone(
        @Path("value") value:String
    ):SearchResp

    @POST("/api/paginationPregnant/offset/{offset}/limit/{limit}")
    suspend fun getPatients(
        @Path("offset") offset:Int,
        @Path("limit") limit:Int,
        @Body body: RequestBody
    ):SearchResp

}