package com.example.sampleapp.feature.sample

import com.example.sampleapp.core.model.Shopping

sealed interface SampleUiState {
    object Loading : SampleUiState

    data class Success(val shoppingItems: List<Shopping> = listOf()) : SampleUiState
}

