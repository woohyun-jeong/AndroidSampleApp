package com.example.sampleapp.core.data.event

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object NetworkChangeEventObserver {
    data class Event(
        val isNetworkChange: Boolean,
    )

    val eventPublisher = MutableSharedFlow<Event>(
        replay = 1,
        extraBufferCapacity = 20,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val eventSubscriber =  eventPublisher.asSharedFlow()
}