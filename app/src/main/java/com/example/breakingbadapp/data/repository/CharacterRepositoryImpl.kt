package com.example.breakingbadapp.data.repository

import androidx.lifecycle.LiveData
import com.example.breakingbadapp.data.network.CharacterDao
import com.example.breakingbadapp.domain.model.CharacterModelItem
import com.example.breakingbadapp.domain.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : CharacterRepository {
    override fun getCharacters(): LiveData<List<CharacterModelItem>> = characterDao.getCharacters()

    override suspend fun deleteCharacter(character: CharacterModelItem) = withContext(Dispatchers.IO) {
        characterDao.delete(
            character
        )
    }

    override suspend fun insertCharacter(character: CharacterModelItem) = withContext(Dispatchers.IO) {
            characterDao.insert(
                character
            )
    }
}