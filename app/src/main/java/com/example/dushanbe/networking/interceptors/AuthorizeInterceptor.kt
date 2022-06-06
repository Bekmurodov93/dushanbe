/*
 * Created by Qutbiddin Bobomulloev on 3/4/19 11:30 AM
 * Copyright © 2019 Alif Capital. All rights reserved.
 * Last modified 3/4/19 11:30 AM
 */

package com.example.dushanbe.networking.interceptors

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.dushanbe.utils.Constants.AUTH_TOKEN
import okhttp3.*
import java.io.IOException
import javax.inject.Inject
class AuthorizationInterceptor @Inject constructor(private val prefs:SharedPreferences,
                                                   private val liveNetworkMonitor: LiveNetworkMonitor) : Interceptor {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (liveNetworkMonitor.isConnected()){
            val newRequest = chain.request().signedRequest()
            return chain.proceed(newRequest)
        } else{
           throw InternetUnavailableException("network is unavailable")
        }
    }

    private fun Request.signedRequest(): Request {
        //should be changed to dynamic
        val token = prefs.getString(AUTH_TOKEN,"")?:""
        return newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization","Bearer $token")
            .addHeader("Language", "Ru")
            .build()
    }
}

class InternetUnavailableException(message:String): IOException(message)