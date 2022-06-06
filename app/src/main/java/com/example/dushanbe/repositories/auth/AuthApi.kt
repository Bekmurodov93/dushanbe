package com.example.dushanbe.repositories.auth


import retrofit2.http.*

interface AuthApi {
    @POST("/api/login")
    suspend fun login(@Query("login") login:String,@Query("password") password:String): AuthModel

    @GET("/api/forgotPassword")
    suspend fun forgot(): SupportModel
}