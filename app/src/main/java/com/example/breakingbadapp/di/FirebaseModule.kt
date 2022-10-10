package com.example.breakingbadapp.di

import com.example.breakingbadapp.common.AuthOperationsWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideAuthOperationsWrapper(firebaseAuth: FirebaseAuth) =
        AuthOperationsWrapper(firebaseAuth)




}