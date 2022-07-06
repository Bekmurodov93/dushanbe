package com.example.dushanbe.dependencyInjection

import android.content.Context
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.dushanbe.BuildConfig
import com.example.dushanbe.networking.interceptors.AuthorizationInterceptor
import com.example.dushanbe.networking.interceptors.LiveNetworkMonitor
import com.example.dushanbe.networking.UrlProvider
import com.example.dushanbe.repositories.auth.AuthApi
import com.example.dushanbe.repositories.helper.HelperApi
import com.example.dushanbe.repositories.register.RegisterApi
import com.example.dushanbe.repositories.search.SearchApi
import com.example.dushanbe.utils.Constants.PREF_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun retrofit(urlProvider: UrlProvider,client: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .client(client)
            .baseUrl(urlProvider.getBaseUrl())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }
    }
    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor {
        return ChuckerInterceptor
            .Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L).redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }
    @Singleton
    @Provides
    fun provideAuthInterceptorOkHttpClient(authInterceptor: AuthorizationInterceptor,
                                           chuckerInterceptor: ChuckerInterceptor,
                                           loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(authInterceptor)
            .addInterceptor(chuckerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun getAuthorizationInterceptor(prefs:SharedPreferences,liveNetworkMonitor: LiveNetworkMonitor): AuthorizationInterceptor {
        return AuthorizationInterceptor(prefs,liveNetworkMonitor)
    }

    @Singleton
    @Provides
    fun urlProvider() = UrlProvider()

    @Singleton
    @Provides
    fun getNetworkMonitor(@ApplicationContext context: Context) = LiveNetworkMonitor(context)



    @Singleton
    @Provides
    fun authApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)


    @Singleton
    @Provides
    fun registerApi(retrofit: Retrofit): RegisterApi = retrofit.create(RegisterApi::class.java)

    @Singleton
    @Provides
    fun searchApi(retrofit: Retrofit): SearchApi = retrofit.create(SearchApi::class.java)

    @Singleton
    @Provides
    fun helperApi(retrofit: Retrofit): HelperApi = retrofit.create(HelperApi::class.java)

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            PREF_NAME, Context.MODE_PRIVATE
        )
}