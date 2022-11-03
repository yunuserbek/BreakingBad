package com.example.breakingbadapp.di

import android.content.Context
import com.example.breakingbadapp.common.util.ResourcesProvider
import com.example.breakingbadapp.common.wrappers.AdsOperationsWrapper
import com.example.breakingbadapp.common.wrappers.AuthOperationsWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseUser() = Firebase.auth

    @Provides
    @Singleton
    fun provideAuthOperationsWrapper(resourceProvider: ResourcesProvider, firebaseAuth: FirebaseAuth) =
        AuthOperationsWrapper(resourceProvider, firebaseAuth)

    @Provides
    @Singleton
    fun provideAdsOperationsWrapper(@ApplicationContext context: Context) =
        AdsOperationsWrapper(context)
}