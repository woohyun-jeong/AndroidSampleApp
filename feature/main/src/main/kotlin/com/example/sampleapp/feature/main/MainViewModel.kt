package com.example.sampleapp.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.core.domain.usecase.GetShoppingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getShoppingUseCase: GetShoppingUseCase,
) : ViewModel() {

    init {
        viewModelScope.launch {
            getShoppingUseCase(0)
        }
    }

}
