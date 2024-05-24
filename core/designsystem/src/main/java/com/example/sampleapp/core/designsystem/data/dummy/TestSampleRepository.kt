package com.example.sampleapp.core.designsystem.data.dummy

import com.example.sampleapp.core.designsystem.data.EmptyItemData

class TestSampleRepository {
    fun getAllData(): List<EmptyItemData> {
        return listOf(
            EmptyItemData(0, "test11", "hi test!!!"),
            EmptyItemData(1, "test2", "hello"),
            EmptyItemData(2, "test3", "good"),
            EmptyItemData(3, "test4", "best"),
            EmptyItemData(
                4,
                "test5",
                "Test is LazyColumn Item"
            ),
            EmptyItemData(5, "test6", "hello world")
        )
    }
}