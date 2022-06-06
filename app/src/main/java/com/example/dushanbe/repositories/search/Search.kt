package com.example.dushanbe.repositories.search

import com.example.dushanbe.repositories.register.RegisterModel
import com.google.gson.annotations.SerializedName

data class SearchResp(
    @SerializedName("code")
    val code:Int,

    @SerializedName("message")
    val message:String,

    @SerializedName("payload")
    val payload:List<RegisterModel>
)