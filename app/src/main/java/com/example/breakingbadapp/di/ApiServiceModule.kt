package com.example.breakingbadapp.di

import com.example.breakingbadapp.common.utils.BASE_URL
import com.example.breakingbadapp.data.remote.BreakingApiService
import com.example.breakingbadapp.data.repository.BreakingBadApiRepositoryImpl
import com.example.breakingbadapp.domain.repository.BreakingBadApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun retrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BreakingApiService = retrofit.create(BreakingApiService::class.java)

    @Singleton
    @Provides
    fun provideApiRepository(apiService: BreakingApiService) : BreakingBadApiRepository =
        BreakingBadApiRepositoryImpl(apiService)
}