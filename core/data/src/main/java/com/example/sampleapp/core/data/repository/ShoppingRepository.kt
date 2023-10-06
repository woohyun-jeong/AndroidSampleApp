package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.model.Shopping
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {

    suspend fun getAllShopping(start:Int = 1): Flow<List<Shopping>>

    suspend fun getSearchShopping(
        query:String,
        sort:String,
        start:Int = 1,
    ): Flow<List<Shopping>>

}
