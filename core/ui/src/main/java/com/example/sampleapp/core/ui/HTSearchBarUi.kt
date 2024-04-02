package com.example.sampleapp.core.ui

import HTSearchBarView
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sampleapp.core.designsystem.component.DefaultVerifyType
import com.example.sampleapp.core.designsystem.component.Verification
import com.example.sampleapp.core.designsystem.component.VerifyType
import com.example.sampleapp.core.designsystem.theme.LocalTypography

@Preview(widthDp = 360, heightDp = 100)
@Composable
fun HTSearchBar(
    textField: HTSearchBarView.SearchBarTextField? = null,
    button: HTSearchBarView.SearchBarButton? = null
) {
    val inputTextStyle = LocalTypography.current.bodyMediumR.copy()
    val hintTextStyle = LocalTypography.current.bodyMediumR.copy(color = Color.LightGray)
    val buttonTextStyle = LocalTypography.current.bodyLargeR.copy(color = Color.Black)

    val searchBarTextField = textField ?: HTSearchBarView.SearchBarTextField(
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

    val searchBarButton = button ?: HTSearchBarView.SearchBarButton(
        textStyle = buttonTextStyle,
        buttonText = "취소",
        shape = null,
        buttonListener = object : HTSearchBarView.SearchButtonListener {
            override fun onClick(inputText: String) {
                Log.d("HTSearchBar", "SearchButtonListener inputText = $inputText")
            }
        }
    )

    val view = object : HTSearchBarView(
        layoutModifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .height(90.dp)
            .padding(20.dp),
        searchTextField = searchBarTextField,
        searchBarButton = searchBarButton
    ) {

    }

    view.OnDraw()
}