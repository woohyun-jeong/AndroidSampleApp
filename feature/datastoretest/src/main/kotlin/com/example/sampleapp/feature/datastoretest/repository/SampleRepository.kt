package com.example.sampleapp.feature.datastoretest.repository

import androidx.datastore.core.DataStore
import com.example.sampleapp.Sample
import kotlinx.coroutines.flow.Flow

class SampleRepository(private val sampleDataStore: DataStore<Sample>) {
    val flow: Flow<Sample> = sampleDataStore.data

    suspend fun increaseCounter() {
        sampleDataStore.updateData { sample ->
            sample
                .toBuilder()
                .setCounter(sample.counter + 1)
                .build()
        }
    }
    suspend fun decreaseCounter() {
        sampleDataStore.updateData { sample ->
            sample
                .toBuilder()
                .setCounter(sample.counter - 1)
                .build()
        }
    }
}