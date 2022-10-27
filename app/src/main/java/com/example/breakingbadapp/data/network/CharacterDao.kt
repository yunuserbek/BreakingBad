package com.example.breakingbadapp.data.network

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.breakingbadapp.domain.model.CharacterModelItem
@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(character: CharacterModelItem)

    @Delete
    suspend fun delete(character: CharacterModelItem)

    @Query("SELECT * FROM character_model")
    fun getCharacters(): LiveData<List<CharacterModelItem>>
}