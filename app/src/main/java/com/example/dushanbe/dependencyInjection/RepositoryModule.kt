package com.example.dushanbe.dependencyInjection

import com.example.dushanbe.networking.interceptors.LiveNetworkMonitor
import com.example.dushanbe.networking.interceptors.NetworkMonitor
import com.example.dushanbe.repositories.auth.AuthRepository
import com.example.dushanbe.repositories.auth.AuthRepositoryImpl
import com.example.dushanbe.repositories.helper.HelperRepository
import com.example.dushanbe.repositories.helper.HelperRepositoryImpl
import com.example.dushanbe.repositories.register.RegisterRepository
import com.example.dushanbe.repositories.register.RegisterRepositoryImpl
import com.example.dushanbe.repositories.search.SearchRepository
import com.example.dushanbe.repositories.search.SearchRepositoryImpl
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