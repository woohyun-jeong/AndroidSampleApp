package com.example.sampleapp.core.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Components Common Spacing Size
 *
 * @constructor
 * define DP
 *
 * @param size
 */
enum class Spacing(size: Dp) {
    XXXS(2.dp),
    XXS(4.dp),
    XS(8.dp),
    S(12.dp),
    M(16.dp),
    L(20.dp),
    XL(24.dp)
}

/**
 * Components Common Radius Size
 *
 * @constructor
 * define DP
 *
 * @param size
 */
enum class Radius(size: Dp) {
    XXS(4.dp),
    XS(8.dp),
    S(12.dp),
    M(16.dp),
    L(20.dp),
    XL(24.dp),
    Full(50.dp)
}