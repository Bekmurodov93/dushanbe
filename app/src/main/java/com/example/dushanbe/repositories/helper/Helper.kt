package com.example.dushanbe.repositories.helper

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
data class HelperHospitalResp(
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("payload")
    val payload: List<Hospital>
)

@Keep
@Entity(tableName = "hospitals")
data class Hospital(
    @SerializedName("id")
    @PrimaryKey
    val id:Int,
    @SerializedName("title")
    val title:String
)

@Keep
data class HelperHospitalTypeResp(
    @SerializedName("code")
    val code:Int,
    @SerializedName("message")
    val message:String,
    @SerializedName("payload")
    val payload: List<HospitalType>
)
@Keep
@Entity(tableName = "hospital_type")
data class HospitalType(
    @SerializedName("id")
    @PrimaryKey
    val id:Int,
    @SerializedName("title")
    val title:String
)