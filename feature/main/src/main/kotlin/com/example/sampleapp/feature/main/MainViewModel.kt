package com.example.sampleapp.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.core.domain.usecase.GetKoreaMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    private val getKoreaMoviesUseCase: GetKoreaMoviesUseCase,
) : ViewModel() {

//    init {
//        viewModelScope.launch {
//            getKoreaMoviesUseCase(0)
//        }
//    }

}
