package com.example.breakingbadapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.common.Resource
import com.example.breakingbadapp.common.wrappers.AdsOperationsWrapper
import com.example.breakingbadapp.domain.model.CharacterModelItem
import com.example.breakingbadapp.domain.repository.BreakingBadApiRepository
import com.example.breakingbadapp.domain.repository.CharacterRepository
import com.google.android.gms.ads.nativead.NativeAd
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingBadViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val repo: CharacterRepository,
    private val breakingBadApiRepository: BreakingBadApiRepository,
    private val adsOperationsWrapper: AdsOperationsWrapper
    ) : ViewModel() {

    private val characterListState = MutableStateFlow<List<CharacterModelItem>>(emptyList())
    val characterList = characterListState.asStateFlow()
    val characterLoading = MutableLiveData<Boolean>()

    init { getData() }

    private fun getData() {
        viewModelScope.launch {
            breakingBadApiRepository.getCharacters().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        characterListState.value = result.data
                    }
                    is Resource.Loading -> {
                        characterLoading.value = true
                    }
                    is Resource.Error -> {
                        // Network listener..
                    }
                }
            }
        }
    }

     fun loadNativeAds(
         onLoadedAd: (NativeAd) -> Unit,
         onAdFailedToLoad: (String) -> Unit
     ) = viewModelScope.launch {
        adsOperationsWrapper.loadNativeAds(
            { nativeAd ->
                characterLoading.postValue(false)
                onLoadedAd(nativeAd)
            },
            { msg ->
                onAdFailedToLoad(msg)
            }
        )
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











