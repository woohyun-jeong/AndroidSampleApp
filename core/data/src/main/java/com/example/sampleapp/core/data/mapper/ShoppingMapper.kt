package com.example.sampleapp.core.data.mapper

import com.example.sampleapp.core.data.api.model.ShoppingItemResponse
import com.example.sampleapp.core.model.Shopping

internal fun ShoppingItemResponse.toData(): Shopping =
    Shopping(
        title = this.title,
        linkUrl = this.link,
        imageUrl = this.image,
        lPrice = this.lPrice,
        hPrice = this.hPrice
    )
