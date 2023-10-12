package com.example.sampleapp.feature.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.core.domain.usecase.GetShoppingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getShoppingUseCase: GetShoppingUseCase,
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    private val _shoppingUiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val shoppingUiState: StateFlow<MainUiState> = _shoppingUiState

    var page: Int = 0
        set(value) {
            field = value
            Log.d("MainViewModel","page value = $value")
            executeGetAllShopping(value)
        }

    private fun executeGetAllShopping(page: Int) {
        viewModelScope.launch {
            combine(
                shoppingUiState,
                getShoppingUseCase(page),
            ) { shoppingUiState, shoppingItems ->
                when (shoppingUiState) {
                    is MainUiState.Loading -> {
                        Log.d("MainViewModel","executeGetAllShopping Loading page = $page, shoppingItems = $shoppingItems")
                        MainUiState.Success(shoppingItems)
                    }
                    is MainUiState.Success -> {
                        Log.d("MainViewModel","executeGetAllShopping Success page = $page, shoppingItems = $shoppingItems")
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
