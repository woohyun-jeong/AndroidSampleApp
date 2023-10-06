package com.example.sampleapp.core.data.repository

import com.example.sampleapp.core.data.api.ShoppingSearchApi
import com.example.sampleapp.core.data.mapper.toData
import com.example.sampleapp.core.model.Shopping
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class DefaultShoppingRepository @Inject constructor(
    private val shoppingSearchApi: ShoppingSearchApi
) : ShoppingRepository {

    override suspend fun getAllShopping(start: Int): Flow<List<Shopping>> {
        return flow {
            val response = shoppingSearchApi.getShoppingItems(
                query = ShoppingSearchApi.NAVER_QUERY_DEFAULT,
                start = start
            )

            response
                .items
                .asSequence()
                .map {
                    it.title = getTitleWithRemovedSearchTag(it.title, ShoppingSearchApi.NAVER_QUERY_DEFAULT)
                    return@map it.toData()
                }.also {
                    emit(it.toList())
                }
        }
    }

    override suspend fun getSearchShopping(
        query: String,
        sort: String,
        start: Int
    ): Flow<List<Shopping>> {
        return flow {
            val response = shoppingSearchApi.getShoppingItemsSort(
                query = query,
                start = start,
                sort = sort
            )

            response
                .items
                .asSequence()
                .map {
                    it.title = getTitleWithRemovedSearchTag(it.title, query)
                    return@map it.toData()
                }.also {
                    emit(it.toList())
                }
        }
    }

    private fun getTitleWithRemovedSearchTag(title: String, query: String): String {
        return title.replace("<b>$query</b>", "")
    }
}
