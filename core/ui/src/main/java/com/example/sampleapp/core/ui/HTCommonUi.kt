package com.example.sampleapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.designsystem.component.DefaultVerifyType
import com.example.sampleapp.core.designsystem.component.DefaultVerifyTypeVersion2
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsView
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsView2
import com.example.sampleapp.core.designsystem.component.VerifyType
import com.example.sampleapp.core.designsystem.theme.LocalTypography

/**
 * Composable
 * 단점
 * - Composable이 Common Module로 빠져서 기능을 추가하려면 Common Module 쪽에서 코드 추가 필요
 * - fun 형태라서 상속도 불가능함
 * 장점
 *
 * - Compose라서 style 같은 경우 LocalContentColor provides contentColor 이와 같이 스타일 바뀌는 느낌으로 가능
 *
 */
@Composable
fun HTInputCheckTextFields() {
    val image = HTInputCheckTextFieldsView.Image(
        id = R.drawable.ic_contributor_placeholder_darkmode,
        size = 45.dp,
        contentScale = ContentScale.Fit
    )

    val textStyle = LocalTypography.current.headlineLargeSB.copy(textAlign = TextAlign.End)

    val view = HTInputCheckTextFieldsView(
        image = image,
        textStyle = textStyle,
        maxLength = 20,
        verification = object : HTInputCheckTextFieldsView.Verification<DefaultVerifyType> {
            override fun verify(input: String): DefaultVerifyType {
                return when (input.length) {
                    1 -> DefaultVerifyType.VerifyMaxInputTextError
                    2 -> DefaultVerifyType.VerifyAlreadyExistTextError
                    3 -> DefaultVerifyType.VerifyTextVerifyError
                    else -> DefaultVerifyType.VerifyOk
                }
            }
        })

    view.OnDraw()
}

@Composable
fun HTInputCheckTextFields2() {
    val image = HTInputCheckTextFieldsView.Image(
        id = R.drawable.ic_contributor_placeholder_lightmode,
        size = 45.dp,
        contentScale = ContentScale.Fit
    )

    val textStyle = LocalTypography.current.headlineLargeSB.copy(textAlign = TextAlign.End)

    val view = HTInputCheckTextFieldsView2(
        image = image,
        textStyle = textStyle,
        maxLength = 20,
        verification = object : HTInputCheckTextFieldsView.Verification<VerifyType> {
            override fun verify(input: String): VerifyType {
                return when (input.length) {
                    1 -> DefaultVerifyType.VerifyMaxInputTextError
                    2 -> DefaultVerifyType.VerifyAlreadyExistTextError
                    3 -> DefaultVerifyType.VerifyTextVerifyError
                    4 -> DefaultVerifyTypeVersion2.VerifyOk2
                    else -> DefaultVerifyType.VerifyOk
                }
            }
        })

    view.OnDraw()
}

