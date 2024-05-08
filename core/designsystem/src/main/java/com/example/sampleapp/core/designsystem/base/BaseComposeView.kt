package com.example.sampleapp.core.designsystem.base

import androidx.compose.runtime.Composable

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
        tag: String, cause: Throwable
    ) : Throwable(tag, cause)

    /**
     * 기본 Style 적용 및 상속 구현하기 위한 Interface
     */
    interface ComposeViewStyle<type : BaseStyle> {

        /**
         * 사용할 Style Type 정의
         *
         * @return
         */
        fun defineStyleType(): type
    }
}

/**
 * Logic 관련 Interface
 */
interface BaseLogic


/**
 * Style 관련 Interface
 */
interface BaseStyle