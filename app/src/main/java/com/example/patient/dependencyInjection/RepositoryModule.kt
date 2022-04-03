package com.example.patient.dependencyInjection

import com.example.patient.networking.interceptors.LiveNetworkMonitor
import com.example.patient.networking.interceptors.NetworkMonitor
import com.example.patient.repositories.auth.AuthRepository
import com.example.patient.repositories.auth.AuthRepositoryImpl
import com.example.patient.repositories.helper.HelperRepository
import com.example.patient.repositories.helper.HelperRepositoryImpl
import com.example.patient.repositories.register.RegisterRepository
import com.example.patient.repositories.register.RegisterRepositoryImpl
import com.example.patient.repositories.search.SearchRepository
import com.example.patient.repositories.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun provideAuthRepository(repoImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideRegisterRepository(repoImpl: RegisterRepositoryImpl): RegisterRepository

    @Binds
    fun provideHelperRepository(repoImpl: HelperRepositoryImpl): HelperRepository

    @Binds
    fun provideNetworkMonitor(repoImpl: LiveNetworkMonitor):NetworkMonitor

    @Binds
    fun provideSearchRepository(repoImpl:SearchRepositoryImpl):SearchRepository
}