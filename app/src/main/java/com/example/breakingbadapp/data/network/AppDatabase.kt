package com.example.breakingbadapp.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.breakingbadapp.domain.model.CharacterModelItem

@Database(
    entities = [CharacterModelItem::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}