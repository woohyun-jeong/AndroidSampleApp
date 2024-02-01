package com.example.sampleapp.core.data.event

import android.os.Bundle
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object EventObserver {
    data class Event(
        val id: String,
        val data: Bundle
    )

    val eventPublisher = MutableSharedFlow<Event>(
        replay = 1,
        extraBufferCapacity = 20,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val eventSubscriber =  eventPublisher.asSharedFlow()
}