package com.example.breakingbadapp.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.BreakingApiService
import com.example.breakingbadapp.CharacterDao
import com.example.breakingbadapp.Model.CharacterModelItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingBadViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val apiService: BreakingApiService,
    private val characterDao: CharacterDao,

) : ViewModel() {
    val _breakingList = MutableLiveData<List<CharacterModelItem>>()
    val countryLoading = MutableLiveData<Boolean>()

    init {
        getData()
    }

    fun getData() {
        countryLoading.value = true
        viewModelScope.launch {
            _breakingList.value = apiService.allBrakingBad()
            countryLoading.value = false
        }
    }

    fun getFavoriteArticles() = characterDao.getCharacter()

    fun addArticleToFavorites(character: CharacterModelItem) = viewModelScope.launch {
        characterDao.insert(character)
    }

    fun removeArticleFromFavorites(character: CharacterModelItem) = viewModelScope.launch {
        characterDao.delete(character)
    }
    fun signOut() {
        firebaseAuth.signOut()

    }

}











