package com.example.sampleapp.core.designsystem.data

import androidx.annotation.DrawableRes
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import com.example.sampleapp.core.designsystem.base.BaseViewData

/**
 * HTInputCheckTextFieldsView에 Image를 위한 Data Class
 *
 * @property id
 * @property size
 * @property contentScale
 */
data class Image(
    @DrawableRes val id: Int, val size: Dp, val contentScale: ContentScale
) : BaseViewData