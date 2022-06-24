package com.example.breakingbadapp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.breakingbadapp.Model.CharacterModelItem
@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(character: CharacterModelItem)

    @Delete
    suspend fun delete(character: CharacterModelItem)

    @Query("SELECT * FROM character_model")
    fun getCharacter(): LiveData<List<CharacterModelItem>>
}