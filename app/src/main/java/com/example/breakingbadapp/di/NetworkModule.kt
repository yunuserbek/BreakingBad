package com.example.breakingbadapp.di

import android.content.Context
import androidx.room.Room
import com.example.breakingbadapp.data.remote.BreakingApiService
import com.example.breakingbadapp.data.network.CharacterDao
import com.example.breakingbadapp.data.network.AppDatabase
import com.example.breakingbadapp.common.utils.BASE_URL
import com.example.breakingbadapp.data.repository.CharacterRepositoryImpl
import com.example.breakingbadapp.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun retrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): BreakingApiService = retrofit.create(BreakingApiService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java,"DATABASE_NAME")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(
        db: AppDatabase
    ): CharacterDao = db.characterDao()

    @Provides
    @Singleton
    fun provideCharacterRepository(
        characterDao: CharacterDao
    ) : CharacterRepository = CharacterRepositoryImpl(
        characterDao = characterDao
    )

}