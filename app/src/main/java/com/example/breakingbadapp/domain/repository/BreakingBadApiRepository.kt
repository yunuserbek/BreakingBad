package com.example.breakingbadapp.domain.repository

import com.example.breakingbadapp.common.Resource
import com.example.breakingbadapp.domain.model.CharacterModelItem
import kotlinx.coroutines.flow.Flow

interface BreakingBadApiRepository  {
    suspend fun getCharacters() : Flow<Resource<List<CharacterModelItem>>>
}