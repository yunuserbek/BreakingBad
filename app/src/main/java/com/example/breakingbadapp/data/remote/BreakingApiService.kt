package com.example.breakingbadapp.data.remote

import com.example.breakingbadapp.domain.model.CharacterModelItem
import com.example.breakingbadapp.common.utils.END_POINT
import retrofit2.http.GET

interface BreakingApiService {
    @GET(END_POINT)
    suspend fun getCharacters() : List<CharacterModelItem>
}