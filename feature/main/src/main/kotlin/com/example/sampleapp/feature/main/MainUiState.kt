package com.example.sampleapp.feature.main

import androidx.compose.runtime.Stable
import com.example.sampleapp.core.model.Shopping
import kotlinx.collections.immutable.PersistentList

sealed interface MainUiState {
    object Loading : MainUiState

    data class Success(val shoppingItems: List<Shopping> = listOf()) : MainUiState
}

