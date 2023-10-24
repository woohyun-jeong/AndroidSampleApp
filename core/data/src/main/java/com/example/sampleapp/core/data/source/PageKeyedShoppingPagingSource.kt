package com.example.sampleapp.core.data.source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sampleapp.core.data.api.ShoppingSearchApi
import com.example.sampleapp.core.data.mapper.toData
import com.example.sampleapp.core.model.Shopping
import javax.inject.Inject

class PageKeyedShoppingPagingSource @Inject constructor(
    private val api: ShoppingSearchApi
) : PagingSource<Int, Shopping>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Shopping> {
        val key = params.key ?: 1
        val result = kotlin.runCatching {
            api.getShoppingItems(
                query = ShoppingSearchApi.NAVER_QUERY_DEFAULT,
                start = key
            )
        }
        Log.d("PageKeyedShoppingPagingSource", "params key = ${params.key}, func key = $key")
        Log.d("PageKeyedShoppingPagingSource", "result = ${result.getOrNull()}")

        return if(result.isSuccess){
            val list = result.getOrNull()?.items ?: return LoadResult.Page(
                    data = arrayListOf(),
                    prevKey = key - 1,
                    nextKey = key + 1
                )

            LoadResult.Page(
                data = list.asSequence().map { it.toData() }.toList(),
                prevKey = key - 1,
                nextKey = key + 1
            )
        }else{
            LoadResult.Error(result.exceptionOrNull() ?: Throwable())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Shopping>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val prevKey = state.closestPageToPosition(anchorPosition)?.prevKey
            Log.d("PageKeyedShoppingPagingSource", "prevKey = $prevKey")
        }
    }

}