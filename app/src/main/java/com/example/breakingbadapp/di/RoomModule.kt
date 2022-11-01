package com.example.breakingbadapp.di

import android.content.Context
import androidx.room.Room
import com.example.breakingbadapp.data.network.AppDatabase
import com.example.breakingbadapp.data.network.CharacterDao
import com.example.breakingbadapp.data.repository.CharacterRepositoryImpl
import com.example.breakingbadapp.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

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