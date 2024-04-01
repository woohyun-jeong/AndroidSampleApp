package com.example.sampleapp.feature.main

import com.example.sampleapp.core.model.Shopping

sealed interface MainUiState {
    object Loading : MainUiState
    data class Success(val shoppingItems: List<Shopping> = listOf()) : MainUiState
}

