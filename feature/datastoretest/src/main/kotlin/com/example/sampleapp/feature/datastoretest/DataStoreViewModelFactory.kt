package com.example.sampleapp.feature.datastoretest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleapp.feature.datastoretest.repository.SampleRepository

class DataStoreViewModelFactory(private val sampleRepository: SampleRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataStoreTestViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataStoreTestViewModel(sampleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}