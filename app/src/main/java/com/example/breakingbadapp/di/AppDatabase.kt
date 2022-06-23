package com.example.breakingbadapp.di

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.breakingbadapp.CharacterDao
import com.example.breakingbadapp.Model.CharacterModelItem

@Database(
    entities = [CharacterModelItem::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}