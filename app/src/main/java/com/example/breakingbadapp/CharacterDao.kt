package com.example.breakingbadapp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.breakingbadapp.Model.CharacterModelItem
@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(character: CharacterModelItem)

    @Update
    suspend fun update(character: CharacterModelItem)

    @Delete
    suspend fun delete(character: CharacterModelItem)
    @Query("SELECT * from character_model WHERE id = :id")
    fun getCharacter(id: Int): LiveData<CharacterModelItem>
}