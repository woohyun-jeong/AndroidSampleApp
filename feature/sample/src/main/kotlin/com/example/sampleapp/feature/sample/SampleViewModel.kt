package com.example.sampleapp.feature.sample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.sampleapp.core.data.api.ShoppingSearchApi
import com.example.sampleapp.core.data.source.PageKeyedShoppingPagingSource
import com.example.sampleapp.core.domain.usecase.GetShoppingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val getShoppingUseCase: GetShoppingUseCase,
    private val shoppingPagingSource: PageKeyedShoppingPagingSource,
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    private val _shoppingUiState = MutableStateFlow<SampleUiState>(SampleUiState.Loading)
    val shoppingUiState: StateFlow<SampleUiState> = _shoppingUiState

    val flowShoppingItem = Pager(
        PagingConfig(pageSize = ShoppingSearchApi.NAVER_DISPLAY_DEFAULT_COUNT)
    ) {
        shoppingPagingSource
    }.flow.cachedIn(viewModelScope)

    private fun executeGetAllShopping(page: Int) {
        viewModelScope.launch {
            combine(
                shoppingUiState,
                getShoppingUseCase(page),
            ) { shoppingUiState, shoppingItems ->
                when (shoppingUiState) {
                    is SampleUiState.Loading -> {
                        Log.d("MainViewModel","executeGetAllShopping Loading page = $page, shoppingItems count = ${shoppingItems.count()}")
                        SampleUiState.Success(shoppingItems)
                    }
                    is SampleUiState.Success -> {
                        Log.d("MainViewModel","executeGetAllShopping Success page = $page, shoppingItems count = ${shoppingItems.count()}")
                        shoppingUiState.copy(shoppingItems)
                    }
                }
            }.catch { throwable ->
                _errorFlow.emit(throwable)
            }.collect {
                Log.d("MainViewModel","executeGetAllShopping Collect page = $page, shoppingItems = $it")
                _shoppingUiState.value = it
            }
        }
    }

}
