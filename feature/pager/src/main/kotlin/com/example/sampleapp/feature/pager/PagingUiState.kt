package com.example.sampleapp.feature.pager

import com.example.sampleapp.core.model.Shopping

sealed interface PagingUiState {
    object Loading : PagingUiState

    data class Success(val shoppingItems: List<Shopping> = listOf()) : PagingUiState
}

