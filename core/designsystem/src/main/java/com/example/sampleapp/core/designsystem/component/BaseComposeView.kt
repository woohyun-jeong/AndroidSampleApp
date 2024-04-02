package com.example.sampleapp.core.designsystem.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

/**
 * Compose 그리기 관련 Interface
 */
interface BaseComposeView {
    @Composable
    fun OnDraw()

    /**
     * Compose View 구성 중 발생한 에러 통합
     *
     * @param tag : 구분자
     * @param cause : Throwable 형태 에러 이유
     */
    class ComposeViewError(
        tag: String,
        cause: Throwable
    ) : Throwable(tag, cause)
}

/**
 * Logic 관련 Interface
 */
interface BaseLogic