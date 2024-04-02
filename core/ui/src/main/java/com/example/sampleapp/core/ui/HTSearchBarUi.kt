package com.example.sampleapp.core.ui

import HTSearchBarView
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.designsystem.component.DefaultVerifyType
import com.example.sampleapp.core.designsystem.component.Verification
import com.example.sampleapp.core.designsystem.component.VerifyType
import com.example.sampleapp.core.designsystem.theme.LocalTypography

@Preview(widthDp = 360, heightDp = 100)
@Composable
fun HTSearchBar() {
    val inputTextStyle = LocalTypography.current.bodyMediumR.copy()
    val hintTextStyle = LocalTypography.current.bodyMediumR.copy(color = Color.LightGray)

    val searchBarTextField = HTSearchBarView.SearchBarTextField(
        maxLength = 20,
        inputHint = "검색어 입력해주세요.",
        inputTextStyle = inputTextStyle,
        hintTextStyle = hintTextStyle,
        textVerification = object : Verification<VerifyType> {
            override fun verify(input: String): VerifyType {
                Log.d("HTSearchBar", "onInputTextChange inputText = $input")
                return DefaultVerifyType.VerifyOk
            }
        }
    )

    val searchBarButton = HTSearchBarView.SearchBarButton(
        textStyle = inputTextStyle,
        buttonText = "취소",
        shape = null,
        onClickButton = {
            Log.d("HTSearchBar", "onClickButton Event Start")
        }
    )

    val view = object : HTSearchBarView(
        searchTextField = searchBarTextField,
        searchBarButton = searchBarButton
    ) {

    }

    view.OnDraw()
}