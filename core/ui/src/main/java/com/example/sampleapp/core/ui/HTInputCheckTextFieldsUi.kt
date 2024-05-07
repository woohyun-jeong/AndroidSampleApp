package com.example.sampleapp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.designsystem.base.DefaultVerifyType
import com.example.sampleapp.core.designsystem.base.Verification
import com.example.sampleapp.core.designsystem.base.VerifyType
import com.example.sampleapp.core.designsystem.component.DefaultVerifyTypeVersion2
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsStyle
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsView
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsView2
import com.example.sampleapp.core.designsystem.component.HTInputCheckTextFieldsView3
import com.example.sampleapp.core.designsystem.theme.Typography

private val style = HTInputCheckTextFieldsStyle(
    image = HTInputCheckTextFieldsView.Image(
        id = R.drawable.ic_contributor_placeholder_lightmode,
        size = 45.dp,
        contentScale = ContentScale.Fit
    ),
    textStyle = Typography.headlineLargeSB.copy(textAlign = TextAlign.End)
)

@Preview
@Composable
fun HTInputCheckTextFields() {
    val view = object : HTInputCheckTextFieldsView(
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
        }) {
        override fun defineStyleType(): HTInputCheckTextFieldsStyle {
            return style
        }
    }

    view.OnDraw()
}

@Preview
@Composable
fun HTInputCheckTextFields2() {
    val view = HTInputCheckTextFieldsView2(
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
    val view = HTInputCheckTextFieldsView3(
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
