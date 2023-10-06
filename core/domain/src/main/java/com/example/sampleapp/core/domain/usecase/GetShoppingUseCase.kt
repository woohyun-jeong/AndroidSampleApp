package com.example.sampleapp.core.domain.usecase

import com.example.sampleapp.core.data.repository.ShoppingRepository
import com.example.sampleapp.core.model.Shopping
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingUseCase @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
) {

    suspend operator fun invoke(start: Int): Flow<Shopping> {
        return shoppingRepository.getAllShopping(start)
    }

}