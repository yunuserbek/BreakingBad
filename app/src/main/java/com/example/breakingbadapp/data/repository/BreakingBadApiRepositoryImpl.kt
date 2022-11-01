package com.example.breakingbadapp.data.repository

import com.example.breakingbadapp.common.Resource
import com.example.breakingbadapp.data.remote.BreakingApiService
import com.example.breakingbadapp.domain.model.CharacterModelItem
import com.example.breakingbadapp.domain.repository.BreakingBadApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BreakingBadApiRepositoryImpl @Inject constructor(
    private val apiService: BreakingApiService
) : BreakingBadApiRepository {

    override suspend fun getCharacters(): Flow<Resource<List<CharacterModelItem>>> = flow {
            try {
                emit(Resource.Loading)
                emit(Resource.Success(apiService.getCharacters()))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }

}