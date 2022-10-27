package com.example.breakingbadapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.data.remote.BreakingApiService
import com.example.breakingbadapp.domain.model.CharacterModelItem
import com.example.breakingbadapp.domain.repository.CharacterRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingBadViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val apiService: BreakingApiService,
    private val repo: CharacterRepository
    ) : ViewModel() {

    val _breakingList = MutableLiveData<List<CharacterModelItem>>()
    val countryLoading = MutableLiveData<Boolean>()

    init { getData() }

    private fun getData() {
        countryLoading.value = true
        viewModelScope.launch {
            _breakingList.value = apiService.allBrakingBad()
            countryLoading.value = false
        }
    }

    fun getFavoriteArticles() = repo.getCharacters()

    fun addArticleToFavorites(character: CharacterModelItem) = viewModelScope.launch {
        repo.insertCharacter(character)
    }

    fun removeArticleFromFavorites(character: CharacterModelItem) = viewModelScope.launch {
        repo.deleteCharacter(character)
    }
    fun signOut() {
        firebaseAuth.signOut()
    }

}











