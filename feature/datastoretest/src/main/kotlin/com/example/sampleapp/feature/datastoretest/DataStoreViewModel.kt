package com.example.sampleapp.feature.datastoretest


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.core.datastore.TestData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    @Named("test") private val testDataStore: DataStore<TestData>) : ViewModel() {
    private val _uiSharedFlow = MutableSharedFlow<BaseUiState>(
        replay = 10,
        extraBufferCapacity = 20,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val uiSharedFlow = _uiSharedFlow.asSharedFlow()
    val testDataFlow = testDataStore.data
    private val _isTest = mutableStateOf(false)
    val isTest: State<Boolean> = _isTest

    fun updateIsTest(isTest: Boolean) {
        _isTest.value = isTest
    }
    fun updateTest(isTest: Boolean) {
        viewModelScope.launch {
            testDataStore.updateData { testData ->
                testData.toBuilder()
                    .setIsTest(isTest)
                    .build()
            }
        }
    }

    fun updateTestString(test: String) {
        viewModelScope.launch {
            testDataStore.updateData { testData ->
                testData.toBuilder()
                    .setTest(test)
                    .build()
            }
        }
    }


    var currentSettingData: SettingsData? = null
    var currentTestData: TestData? = null

}