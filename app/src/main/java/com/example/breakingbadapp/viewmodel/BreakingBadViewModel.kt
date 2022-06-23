package com.example.breakingbadapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.BreakingApiService
import com.example.breakingbadapp.CharacterDao
import com.example.breakingbadapp.Model.CharacterModelItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingBadViewModel @Inject constructor(
    val apiService: BreakingApiService,
    val characterDao: CharacterDao
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

    fun saveFavorite(character: CharacterModelItem) {
        viewModelScope.launch {
            characterDao.insert(character)
            setIsFavoriteCharacter(character, true)
        }


    }

    fun deleteFromFavorites(character: CharacterModelItem) {
        viewModelScope.launch {
            characterDao.delete(character)
            setIsFavoriteCharacter(character, true)
        }
    }


    private fun setIsFavoriteCharacter(character: CharacterModelItem, isFavorite: Boolean) {
        _breakingList.value?.let {
            // Check if its on Characters List
            if (it.contains(character)) {
                // Get Character and assign isFavorite
                it[it.lastIndexOf(character)].isFavorite = isFavorite
            }
        }
    }
}



