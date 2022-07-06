package com.example.dushanbe.repositories.register

import retrofit2.http.*

interface RegisterApi {

    @POST("/api/createPregnant")
    suspend fun register(@Query("fio") fio:String,
                      @Query("publish_date") publishDate:String,
                      @Query("hospital_type") type:Int,
                      @Query("birthdate") birthday:String,
                      @Query("passport") passport:String,
                      @Query("address") address:String,
                      @Query("phone") phone:String,
                      @Query("phone_ex") phoneEx:String,
                      @Query("info_menstruation") infoMenstruation:String,
                      @Query("info_estimated_date") infoEstimatedDate:String,
                      @Query("info_parity") infoParity:Int,
                      @Query("info_birthpermit") infoBirthPermit:Int
      ): RegisterResp

    @Multipart
    @POST("/api/saveFormPregnantByCode/{code}/form/1")
    suspend fun updateFormFirst(
        @Path("code" ) code:String,
        @Part("fio") fio:String,
        @Part("publish_date") publishDate:String,
        @Part("hospital_type") type:Int,
        @Part("birthdate") birthday:String,
        @Part("passport") passport:String,
        @Part("address") address:String,
        @Part("phone") phone:String,
        @Part("phone_ex") phoneEx:String,
        @Part("info_menstruation") infoMenstruation:String,
        @Part("info_estimated_date") infoEstimatedDate:String,
        @Part("info_parity") infoParity:Int,
        @Part("info_birthpermit") infoBirthPermit:Int
    ):RegisterResp

    @Multipart
    @POST("/api/saveFormPregnantByCode/{code}/form/2")
    suspend fun updateFormSecond(
        @Path("code" ) code:String,
        @Part("ch_visit_date_1") ch_visit_date_1:Int,
        @Part("visit_date_1") visit_date_1:String,
        @Part("ch_visit_date_2") ch_visit_date_2:Int,
        @Part("visit_date_2") visit_date_2:String
    ):RegisterResp

    @Multipart
    @POST("/api/saveFormPregnantByCode/{code}/form/3")
    suspend fun updateFormThird(
        @Path("code" ) code:String,
        @Part("egcy_init_hospital_type") egcy_init_hospital_type:Int,
        @Part("egcy_init_date") egcy_init_date:String,
        @Part("egcy_init_diagnosis") egcy_init_diagnosis:String,
        @Part("egcy_init_treatment") egcy_init_treatment:String,
        @Part("egcy_init_phonogram") egcy_init_phonogram:Int,
        @Part("egcy_init_relatives") egcy_init_relatives:Int,
        @Part("egcy_init_projects") egcy_init_projects:Int,
        @Part("egcy_init_accompanying") egcy_init_accompanying:Int,
        @Part("egcy_init_transport_provided") egcy_init_transport_provided:Int,
        @Part("egcy_init_accompanying_gender") egcy_init_accompanying_gender:String
    ):RegisterResp


    @Multipart
    @POST("/api/saveFormPregnantByCode/{code}/form/4")
    suspend fun updateFormFourth(
        @Path("code" ) code:String,
        @Part("rtn_accept_referral") rtn_accept_referral:Int,
        @Part("ch_rtn_accept_newborn_1") ch_rtn_accept_newborn_1:Int,
        @Part("rtn_accept_newborn_1") rtn_accept_newborn_1:String
    ):RegisterResp

    @Multipart
    @POST("/api/saveFormPregnantByCode/{code}/form/5")
    suspend fun updateFormFifth(
        @Path("code" ) code:String,
        @Part("mlty_maternal") mlty_maternal:Int,
        @Part("mlty_maternal_hospital_id") mlty_maternal_hospital_id:Int,
        @Part("mlty_maternal_cause") mlty_maternal_cause:String,
        @Part("mlty_child") mlty_child:Int,
        @Part("mlty_child_recorded_days") mlty_child_recorded_days:String,
        @Part("mlty_child_hospital_id") mlty_child_hospital_id:Int,
        @Part("mlty_child_cause") mlty_child_cause:String
    ):RegisterResp
}