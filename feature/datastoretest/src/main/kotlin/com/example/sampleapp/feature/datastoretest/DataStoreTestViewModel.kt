package com.example.sampleapp.feature.datastoretest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.Sample
import com.example.sampleapp.feature.datastoretest.repository.SampleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DataStoreTestViewModel(private val sampleRepository: SampleRepository) : ViewModel() {
    val flow: Flow<Sample> = sampleRepository.flow

    fun increaseCounter() {
        viewModelScope.launch { sampleRepository.increaseCounter() }
    }
    fun decreaseCounter() {
        viewModelScope.launch { sampleRepository.decreaseCounter() }
    }
}