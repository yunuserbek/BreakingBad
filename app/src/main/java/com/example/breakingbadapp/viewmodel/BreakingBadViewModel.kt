package com.example.breakingbadapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.breakingbadapp.BreakingApiService
import com.example.breakingbadapp.Model.CharacterModelItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreakingBadViewModel @Inject constructor(
    val apiService: BreakingApiService,
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

}



