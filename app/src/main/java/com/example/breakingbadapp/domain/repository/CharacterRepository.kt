package com.example.breakingbadapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.breakingbadapp.domain.model.CharacterModelItem

interface CharacterRepository {
    fun getCharacters() : LiveData<List<CharacterModelItem>>

    suspend fun deleteCharacter(character: CharacterModelItem)

    suspend fun insertCharacter(character: CharacterModelItem)
}
