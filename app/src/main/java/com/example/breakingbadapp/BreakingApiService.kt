package com.example.breakingbadapp

import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.utils.END_POINT
import retrofit2.http.GET

interface BreakingApiService {
    @GET(END_POINT)
    suspend fun allBrakingBad():List<CharacterModelItem>
}