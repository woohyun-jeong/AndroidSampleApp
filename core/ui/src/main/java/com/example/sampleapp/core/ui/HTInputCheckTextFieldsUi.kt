package com.example.sampleapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.designsystem.component.DefaultVerifyType
import com.example.sampleapp.core.designsystem.component.DefaultVerifyTypeVersion2
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsView
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsView2
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsView3
import com.example.sampleapp.core.designsystem.component.Verification
import com.example.sampleapp.core.designsystem.component.VerifyType
import com.example.sampleapp.core.designsystem.theme.LocalTypography

@Preview
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
        maxLength = 4,
        verification = object : Verification<DefaultVerifyType> {
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

@Preview
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
        verification = object : Verification<VerifyType> {
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

@Preview
@Composable
fun HTInputCheckTextFields3() {
    val image = HTInputCheckTextFieldsView.Image(
        id = R.drawable.ic_contributor_placeholder_lightmode,
        size = 45.dp,
        contentScale = ContentScale.Fit
    )

    val textStyle = LocalTypography.current.headlineLargeSB.copy(textAlign = TextAlign.End)

    val view = HTInputCheckTextFieldsView3(
        image = image,
        textStyle = textStyle,
        maxLength = 20,
        verification = object : Verification<VerifyType> {
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
